exports.install = function (Vue, params) {
    Vue.prototype.doPost = function (option) {
        console.log(option);
    };
}