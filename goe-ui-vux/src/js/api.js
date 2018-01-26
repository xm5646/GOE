exports.install = function (Vue, option) {
  Vue.prototype.api = function (method, params) {
    switch (method) {
      case 'login':
        return 'result'
      default:
        console.log('end')
    }
  }
}
