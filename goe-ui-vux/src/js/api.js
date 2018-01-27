exports.install = function (Vue, option) {
  Vue.prototype.api = function (method, params) {
    if (method === 'login') {
      let result = ''
      const url = 'http://192.168.1.158:8088' + '/user/login'
      Vue.$http.post(url,
        params.params)
        .then(response => {
          if (response.body.success) {
            result = response.body.body
          } else {
            this.password = ''
          }
        }, responseErr => {
          this.password = ''
        })
      return result
    }
  }
}
