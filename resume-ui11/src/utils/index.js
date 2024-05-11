export default {
    getUser: function () {
        return JSON.parse(localStorage.getItem("user"));
    },
    isAdmin: function () {
        let user = this.getUser()
        return user.role != null
    }
}