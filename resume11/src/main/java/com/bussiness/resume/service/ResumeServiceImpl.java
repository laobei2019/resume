package com.bussiness.resume.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.utils.FileUploadUtil;
import com.bussiness.formwork.entity.Formwork;
import com.bussiness.formwork.mapper.FormworkMapper;
import com.bussiness.resume.entity.Resume;
import com.bussiness.resume.mapper.ResumeMapper;
import com.bussiness.resumeHistory.entity.ResumeHistory;
import com.bussiness.resumeHistory.mapper.ResumeHistoryMapper;
import com.bussiness.user.mapper.UserMapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.markdown.MarkdownRenderPolicy;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Resource
    ResumeHistoryMapper historyMapper;
    @Resource
    FormworkMapper formworkMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public boolean saveOrUpdate(Resume entity) {
        final ResumeHistory history = new ResumeHistory();
        history.setResumeId(entity.getId());
        history.setContext(entity.getContext());
        history.setCreateTime(new Date());
        history.setUpdateTime(new Date());
        historyMapper.insert(history);
        return super.saveOrUpdate(entity);
    }

    @Override
    public String preview(String resumeId, String formworkId, String username) {
        Formwork formwork = formworkMapper.selectById(formworkId);
        String templateName = String.format("temp/%s/%s", resumeId, formwork.getFilename());
        final String userId = getBaseMapper().selectById(resumeId).getUserId();
        final String name = userMapper.selectById(userId).getName();
        String resDoc = String.format("temp/%s/%s/%s的简历.docx", resumeId, formworkId, name);
        String resPdf = String.format("temp/%s/%s/%s的简历.pdf", resumeId, formworkId, name);
        File file = new File(templateName);

        file.getParentFile().deleteOnExit();
        file.getParentFile().mkdirs();

        final File file1 = new File(resDoc);
        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
        }
        FileUploadUtil.download(formwork.getFileUrl(), templateName);
        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(templateName, config).render(getResumeMap(resumeId));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(resDoc);
            template.writeAndClose(fileOutputStream);
            convertWordToPdf(resDoc, resPdf);
            fileOutputStream.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return resPdf;
    }

    private Map getResumeMap(String resumeId) {
        if (StringUtils.isEmpty(resumeId)) return new HashMap();
        Resume resume = getById(resumeId);
        String context = resume.getContext();
        JSONArray jsonArray = JSONObject.parseArray(context);
        Map<String, Object> map = new HashMap<>();
        for (Object obj : jsonArray) {
            JSONObject title = (JSONObject) obj;
            String name = title.getString("name");
            String type = title.getString("type");
            switch (type) {
                case "None":
                    map.put(name, stringFliter(title.get("text").toString()));
                    break;
                case "Multiple":
                    map.put(name, stringFliter(title.getJSONArray("datas")));
                    break;
                case "Single":
                    final JSONArray datas = title.getJSONArray("datas");
                    if (datas.size() > 0) {
                        JSONObject data = datas.getJSONObject(0);
                        for (String key : data.keySet()) {
                            map.put(String.format("%s_%s", name, key), stringFliter(data.get(key).toString()));
                        }
                        break;
                    }
            }
        }
        return map;
    }

    private String stringFliter(String str) {
        String[] regs = new String[]{"<p>", "<span>","</p>","<br>"};
        String[] regsLine = new String[]{"<br>","</p><p>"};
        for (String reg : regsLine) {
            str = str.replaceAll(reg, "\n");
        }
        for (String reg : regs) {
            str = str.replaceAll(reg, "");
        }
        return str;
    }

    private JSONArray stringFliter(JSONArray jsonArray) {
        JSONArray res = new JSONArray();
        for (Object o : jsonArray) {
            JSONObject a = new JSONObject();
            final JSONObject object = (JSONObject) o;
            for (String s : object.keySet()) {
                final Object o1 = object.get(s);
                if (o1 instanceof String) a.put(s, stringFliter(o1.toString()));
                else a.put(s, o1);
            }
            res.add(a);
        }
        return res;
    }

    private void convertWordToPdf(String word, String pdf) {
        //实例化Document类的对象
        Document doc = new Document();
        //加载Word
        doc.loadFromFile(word);
        //保存为PDF格式
        doc.saveToFile(pdf, FileFormat.PDF);
    }
}
