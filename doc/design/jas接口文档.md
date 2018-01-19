

# jas接口文档

#除了注册和用户入口接口其余接口都加token认证

## 1.注册

### 1.1验证码

**url** :

/api/wechat/authCode/{phone}

**request**

| param | type   | description | required |
| ----- | ------ | ----------- | -------- |
| phone | String | 手机号         | true     |

**response**

成功

```
{
  "code":0,
 "data":"验证码发送成功"
}
```

### 1.2用户注册

* __Method__
  GET

* __URL__
  api/wechat/register

* __Request__

> 请求参数Json格式

```
{
 "openId":"admin", //用户标识
 "realName":"xxxx", //真实姓名
 "phone":"XXXX", //手机号码
 "captcha":"6666", //验证码
 "type":"0", //类型 0代表病人 1代表医生

}
```

* __response__

成功

```
{
    "code": 0,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDI0Mzk4NjksInN1YiI6IntcInBob25lXCI6XCIxMzY5NjY1NTYyMFwiLFwib3BlbklkXCI6XCJ0ZXN0XCJ9IiwiZXhwIjoxNTAzMzAzODY5fQ.Sji8WZEAbXQt7bLXjk1GlnSO2seMc5XH1SerZJgPbRM"
    }
}
```

失败

```
{
  "code":2,
  "data":"参数错误"
}
```

### 1.3完善用户信息

**url**

api/app/user/update

**request**

| type        | param         | description | required |
| ----------- | ------------- | ----------- | -------- |
| picture     | MultipartFile | 头像          | true     |
| sex         | Integer       | 性别          | true     |
| birth       | Date          | 出生年月        | true     |
| hospital_id | long          | 医院id        | true     |

note:病人注册不选医院参数，医生注册医院必选

**response**

成功

```
{
  "code":0,
  "data":"注册成功"
}
```

失败

```

{
  "code":2,
  "data":"参数错误"
}
```

### 1.4查询用户是否为已注册用户

**url**

api/app/user/isRegister

**request**

| param  | type   | description | required |
| ------ | ------ | ----------- | -------- |
| openId | String | 微信用户id      | true     |

**response**

成功

```
{
"code":0,
"data":"该用户未注册"

}
 

该用户未注册
{
"code":0,
"data":"该用户已注册"

}
```

**失败**

```
{
  "code":2,
  "data":"参数错误"
}
```

## 2.病人

### 2.1.编辑病例

* __Method__
  POST

* __URL__
  api/wechat/ma

* __Request__

> 请求参数Json格式

```
{
 "startTime":"", //开始时间
 "patientId":"1", //病人id
 "illnessState":"1", //病情说明
 "activity":"1", //活动因素不足
 "process":"1", //病程

}
```

**response**

成功

```
{
    "code": 0,
    "data": {
        "id": 2,   //病历档案id
        "patientId": 1,   //病人id
        "surgeryTime": null,  //手术时间
        "startTime": null,
        "illnessState": 1,  //病情说明
        "activity": 1,  //活动因素
        "process": 1,  //病程
        "department": null,  //科室
        "hospital": null,  //医院
        "doctorName": null, //手术医生
        "content": null,//描述
        "createTime": 1502783195000,
        "updateTime": 1502783195000
    }
}
```

失败

```
{
  "code":2,
  "data":"参数错误"
}
```

### 2.2.查看我的设备

****url****

api/app/patient/deviceList

### 2.2.查看设备详情

**url**

api/app/patient/deviceDetail

**request**

**response**

成功

```
{
  "code":0,
  "data":{
    设备信息
  }
}
```

### 2.3.绑定设备

**url**

api/app/patient/bind

**request**

| param | type   | description | required |
| ----- | ------ | ----------- | -------- |
| guid  | String | 设备唯一标识      | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":3,
"data":设备不存在}
```

### 2.4.解除绑定

****url****

api/app/patient/removeBind

**request**

| param | type   | description | required |
| ----- | ------ | ----------- | -------- |
| id    | String | 设备id        | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":“参数错误”}
```

### 2.5.我的康复师

****url****

api/app/patient/therapistList

****request****

### 2.5.查看康复师详情

**url**

api/app/patient/therapist

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 康复师id       | true     |

**response**

成功

```
{"code":0,
"data":{
  康复师信息
}}
```

失败

```
{"code":2,
"data":“参数错误”}
```

### 2.6.选择康复师

********url********

api/app/patient/select

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 康复师id       | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":“参数错误”}
```

### 2.7.查看病例档案

****url****

api/app/patient/record

**request**

**response**

成功

```
{
  "code":0,
  "data":{
    康复档案列表
  }
}
```

### 2.8查看病例档案详情

********url********

api/app/patient/recordDetail

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 档案id        | true     |

**response**

成功

```
{"code":0,
"data":{
  档案详情
}}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 2.9删除档案

api/app/patient/deleteRecord

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 档案id        | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 2.10查看康复训练记录

**url**

api/app/patient/trainRecord

**request**

| param | type   | description | required |
| ----- | ------ | ----------- | -------- |
| time  | String | 训练记录的起始时间段  | false    |
| ep_id | long   | 训练计划id      | true     |

note:1.不传time参数查询最近一次的记录，time不为空根据时间段查询训练记录

​          2.选择周 time 参数为当前时间往前推到周一

​          3.选择月 time参数为当前时间往前推到当月一号

​        



**response**

成功

```
{"code":0,
"data":训练记录信息}
```

### 2.11 查看评估记录

**url**

api/app/patient/assessRecord

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| ep_id | long | 训练计划id      | true     |



### 2.12 编辑康复计划

****url****

api/app/patient/editPlan

**request**

| param        | type    | description | required |
| ------------ | ------- | ----------- | -------- |
| cycle        | String  | 训练周期        | true     |
| time         | String  | 时间段         | true     |
| jolong       | Integer | 训练关节        | true     |
| position     | Integer | 训练部位        | true     |
| dirdirection | Integer | 训练方向        | true     |
| therapist_id | long    | 康复师         | true     |

**训练关节状态说明**

| state | explain |
| ----- | ------- |
| 1     | 肘关节     |
| 2     | 腕关节     |
| 3     | 膝关节     |
| 4     | 裸关节     |
| 5     | 其它      |

**训练部位状态说明**

| state | explain |
| ----- | ------- |
| 1     | 左       |
| 2     | 右       |

**训练方向状态说明**

| state | explain |
| ----- | ------- |
| 1     | 伸展      |
| 2     | 屈曲      |

**Response**

成功

```
{
  "code":0,
  "data":null
}
```

失败

```
{"code":2,
"data":参数错误}
```

### 2.13查看我的康复计划

**Url**

api/app/patient/queryPlan

**Request**

| param | type    | description      | required |
| ----- | ------- | ---------------- | -------- |
| count | Integer | 根据count状态拿到计划的数量 | true     |
| pId   | long    | 病人id             | false    |

**note**：病人查看康复计划pid 为false，医生查看康复计划pid我true

**count状态说明**

| state | explain    |
| ----- | ---------- |
| 1     | 查看最近两次康复计划 |
| 2     | 查看所有康复计划   |

### 2.14康复计划详情

**url**

api/app/patient/planDetail

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 康复计划id      | true     |

**response**

成功

```
{
  "code":0,
  "data":{
    康复计划信息
  }
}
```

失败

```
{"code":2,
"data":参数错误}
```

### 2.15病情说明原因列表查询

* __Method__
  GET

* __URL__

api/wechat/dict/illnessState

* __Request__

 Param | Type | Description

 无

* __Response__

> 成功

```
{
    "code": 0,
    "data": [
        {
            "code": 1,
            "name": "历史遗留"
        },
        {
            "code": 2,
            "name": "近期手术"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```

### 2.16活动不足因素列表查询

* __Method__
  GET

* __URL__

api/wechat/dict/nactivityFactor

* __Request__

 Param | Type | Description

 无

* __Response__

> 成功

```
{
    "code": 0,
    "data": [
        {
            "code": 1,
            "name": "长时间固定"
        },
        {
            "code": 2,
            "name": "关节内源性病变"
        },
        {
            "code": 3,
            "name": "静态生活方式"
        },
        {
            "code": 4,
            "name": "瘫痪/张力异常/失衡"
        },
        {
            "code": 5,
            "name": "姿势排列不正"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```

### 2.17 病程列表查询

* __Method__
  GET

* __URL__

api/wechat/dict/diseaseCourse

* __Request__

 Param | Type | Description

 无

* __Response__

> 成功

```
{
    "code": 0,
    "data": [
        {
            "code": 1,
            "name": "1周"
        },
        {
            "code": 2,
            "name": "2周内"
        },
        {
            "code": 3,
            "name": "3-12周"
        },
        {
            "code": 4,
            "name": "12周以上"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```

## 3.康复师

### 3.1我的患者

**url**

api/app/therapist/myPatient

**request**

| param | type   | description | required |
| ----- | ------ | ----------- | -------- |
| pName | String | 患者名字        | false    |

**response**

成功

```
{"code":0,
"data":{
  患者信息
}}
```

失败

```
{
  "code":4,
  "data":"此患者不存在"
}
```

### 3.2查询患者详情

**url**

api/app/therapist/patientDetail

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| pId   | long | 病人id        | true     |

**response**

成功

```
{"code":0,
"data":{
  病人信息
}}
```

失败

```
{"code":2,
"data":参数错误}
```

### 3.3提交评估报告

**url**

api/app/therapist/report

**request**

| param      | type    | description | required |
| ---------- | ------- | ----------- | -------- |
| time       | String  | 时间段         | true     |
| conclusion | Integer | 评估结论        | true     |
| content    | String  | 评估内容        | true     |
| eq_id      | long    | 康复计划id      | true     |

评估结论状态解释

| state | explain |
| ----- | ------- |
| 1     | 维持      |
| 2     | 改善      |
| 3     | 恶化      |
| 4     | 脱落      |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 3.4康复接受绑定**病人

**url**

api/app/therapist/agree

**request**

| param | type | description | required |
| ----- | ---- | ----------- | -------- |
| id    | long | 病人id        | true     |

**response**

成功

```

{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 3.5康复师更新病人康复计划（2.12）



## 4.用户

### 4.1我的积分

**url**

api/app/user/longegral

**request**

| param | type    | descripton             | required |
| ----- | ------- | ---------------------- | -------- |
| type  | Integer | 积分变化（只有用的积分；赚取积分和用掉积分） | true     |

number状态解释

| state | explain       |
| ----- | ------------- |
| 1     | 查询赚取积分和用掉积分列表 |
| 2     | 查看只有用掉积分列表    |

**reponse****

成功

```

{"code":0,
"data":{
  我的积分列表
}}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 4.2积分兑换记录

**url**

api/app/user/exchangeRecord

**request**

**reponse**

成功

```

{"code":0
"data":{
  积分兑换记录列表
}}
```

### 4.3积分兑换优惠券

**url**

api/app/user/exchange

**request**

| param | type | description | requiredid |
| ----- | ---- | ----------- | ---------- |
| id    | long | 积分id        | true       |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 4.4 我的优惠券

**url**

api/app/user/ticket

**request**

| param | type    | description | required |
| ----- | ------- | ----------- | -------- |
| type  | Integer | 优惠券(可用，历史)  | true     |



number状态解释

| state | explain |
| ----- | ------- |
| 1     | 查询可用优惠券 |
| 2     | 查询历史优惠券 |

**response**

成功

```

{"code":0,
"data":{
  优惠券列表
}}
```

失败

```
{"code":0,
"data":"参数错误"}
```

## 5.我的

### 5.1用户详情

url**

api/app/user/userDetail

**request**

**response**

成功

```
{"code":0,
"data":{
  用户信息
}}
```

失败

```
{"code":5,
"data":token验证失败}
```

### 5.2编辑我的头像

**url**

api/app/user/editImg

**request**

| param | type          | description | required |
| ----- | ------------- | ----------- | -------- |
| img   | MultipartFile | 我的头像图片      | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 5.3编辑我的姓名/性别/年龄

**url:**

api/app/user/edit

**request**

| param    | type    | description | required |
| -------- | ------- | ----------- | -------- |
| userName | String  | 用户名         | false    |
| sex      | Integer | 性别          | false    |
| age      | Integer | 年龄          | false    |

sex状态解释

| state | explain |
| ----- | ------- |
| 1     | 男       |
| 2     | 女       |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```
### 5.4 修改手机

**url**

api/app/user/setPhone

**request**

| param     | type   | description | required |
| --------- | ------ | ----------- | -------- |
| authoCode | String | 验证码         | true     |
| phone     | String | 手机号         | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```

### 5.5我的医院

**URL**

api/app/user/hospital

**Request**

| param       | type | description | required |
| ----------- | ---- | ----------- | -------- |
| hospital_id | long | 医院id        | true     |

**response**

成功

```
{"code":0,
"data":null}
```

失败

```
{"code":2,
"data":"参数错误"}
```