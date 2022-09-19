// 引入封装好的jsonp
import jsonp from '../js/jsonp.js'
// 假设这里为跨域请求当前城市的接口
export function userLogin(userNmae,password) {
    // 接口地址
    let url = 'http://localhost:30002/user/login'
    // 所需参数
    let datas = {
        'username': userNmae,
        'password': password
    }
    return jsonp(url, datas, {})
}