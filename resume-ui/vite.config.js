import vue from '@vitejs/plugin-vue'
import path from "path";

import {defineConfig} from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

export default defineConfig({

    // ...
    plugins: [
        // ...
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
        vue()
    ],
    build: {
        assetsDir: 'public',
    },
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src/"),
        },
    },
})