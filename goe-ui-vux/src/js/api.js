exports.install = function (Vue, option) {
  Vue.prototype.api = function (method, params) {
    if (method === 'login') {
      console.log('message')
    }
  }
}
