# jas接口文档

#除了注册和用户入口接口其余接口都加token认证

## 1.用户入口

### 1.1 用户入口

* __Method__
  GET

* __URL__:
  /api/wechat/auth/user/{code}

* __Request__

Param | Type | Description 

code| Long | 微信端code(前端获取发送到后端)

* __response__

> 成功

```
{
  "code": 0,
     "data": {
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDI0Mzk4NjksInN1YiI6IntcInBob25lXCI6XCIxMzY5NjY1NTYyMFwiLFwib3BlbklkXCI6XCJ0ZXN0XCJ9IiwiZXhwIjoxNTAzMzAzODY5fQ.Sji8WZEAbXQt7bLXjk1GlnSO2seMc5XH1SerZJgPbRM"
         //如果这个用户是医生会返回状态码(0 未验证，1同意，2拒绝)只有经过后台同意审核的医生才能进入主页面
         "status":1
     }
}
```

> 失败

```
{
  "code": 0,
     "data": {
         "openId": "admin"
     }
}
```

## 2.注册


### 2.1 验证码

* __Method__
  POST

* __URL__:
  /api/wechat/authCode/{phone}

* __Request_
  Param | Type | Description 

phone | String | 手机号码    

* __response__

> 成功

```
{
  "code":0,
 "data":"验证码发送成功"
}
```

### 2.2 用户注册

* __Method__
  POST

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

> 成功

```
{
    "code": 0,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MDI0Mzk4NjksInN1YiI6IntcInBob25lXCI6XCIxMzY5NjY1NTYyMFwiLFwib3BlbklkXCI6XCJ0ZXN0XCJ9IiwiZXhwIjoxNTAzMzAzODY5fQ.Sji8WZEAbXQt7bLXjk1GlnSO2seMc5XH1SerZJgPbRM"
    }
}
```

> 失败

```
{
  "code":2,
  "data":"参数错误"
}
```
### 2.3 病人信息完善

* __Method__
  POST

* __URL__
  /api/wechat/user/update

* __Request__

> 请求参数Json格式

```
{"sex":1, Integer 

 "birthDate":"1502939683", Date 

 "headPortrait":"D:image/image111" String
 } 
```
* __response__
> 成功
```
{ "code": 0,  "data": }
```


### 2.3.1 医生信息完善

* __Method__
  POST

* __URL__
  /api/wechat/user/update

  * __Request__
     Param | Type | Description
     code    String 医院code

> 请求参数Json格式

```
{"sex":1, Integer 

 "birthDate":"1502939683", Date 

 "headPortrait":"D:image/image111" String
 } 
```
* __response__
> 成功
```
{ "code": 0,  "data": }
```

> 失败
```
{
  "code":2,
  "data":"参数错误"
}
```
### 2.3.2 用户完善信息显示默认头像(目前医生和病人默认头像调用 暂时只用当前地址)

* __Method__
    GET

* __URL__
  /api/wechat/user/getHeadImg
  * __Request__
    Param | Type | Description
    userId   Long 用户Id(在注册的时候获取默认头像不用传)
* __response__
> 成功
> {
>   "code": 0,
>   "data": {
    "result": ""
  }
}
>失败
>{
>  "code":2,
>  "data":"参数错误"
>}

### 2.3.2 病人和医生详情显示头像
* __Method__
    GET

* __URL__
  /api/wechat/user/getHeadImg
  * __Request__
    Param | Type | Description
    userId String  不传userId则显示默认头像，如果是病人查看康复师详情则传入康复师userId,康复师查看病人详情则传入病人userId
* __response__
> 成功
> {
>   "code": 0,
>   "data": {
    "result": "D:\\headImg\\1503046931653。.jpg"
  }
}
>失败
>{
>  "code":2,
>  "data":"参数错误"
>}

### 2.3.3 用户头像上传（病人和医生都调用当前地址）
* __Method__
   POST
* __URL__
  /api/wechat/user/headImg
  * __Request__
    Param | Type | Description
     无
* __response__
> 成功
```
{
  "code": 0,
  "data": null
}
```
>失败
```
{
  "code":2,
  "data":"上传错误"
}
```
### 2.4 查看用户详细信息

- __Method__
  GET
- __URL__
  /api/wechat/user/userDetail
- __Request__
  请求参数
  Param | Type | Description
  **无**
* __response__
  成功
```
{
  "code": 0,
  "data": {
"id": 1,
"openId": "admin",
"realName": "admin", //名称
"sex": 1, //性别
"phone": "123456789", //手机号码
"birthDate": 1502877000, //生日
"scoreValue": 14, //总积分
"headPortrait": "", //头像
"createTime": 1501725048000,
"updateTime": 1501725048000
  }
}
```

失败
```
{"code":2,
"data":参数错误}
```
### 2.5 查看医生详细信息
- __Method__
  GET
- __URL__
  /api/wechat/user/doctorDetail
* __Request__

请求参数
Param | Type | Description
无
* __response__
  成功
```
{
  "code": 0,
  "data": {
    "headPortrait": img//123456.jpg, //头像
    "sex": 1, /性别
    "realName": "xiaoli",  //名称
    "hospital": 1, //所在医院
    "department": null //科室
  }
}
```
失败
```
{"code":2,
"data":参数错误}
```
## 3 字典

### 3.1 病情说明列表

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

### 3.2活动不足因素列表

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

### 3.3 病程列表查询

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

### 3.4 照片标签/分类

* __Method__
  GET

* __URL__
  api/wechat/dict/pictureLabel

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
            "name": "术前"
        },
        {
            "code": 2,
            "name": "术后"
        },
        {
            "code": 3,
            "name": "最近一次"
        },
        {
            "code": 4,
            "name": "最大变化"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.5 获取关节列表

* __Method__
  GET

* __URL__
  api/wechat/dict/arthro

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
            "name": "肘关节"
        },
        {
            "code": 2,
            "name": "腕关节"
        },
        {
            "code": 3,
            "name": "膝关节"
        },
        {
            "code": 4,
            "name": "裸关节"
        },
        {
            "code": 5,
            "name": "其他"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.6 获取部位列表

* __Method__
  GET

* __URL__
  api/wechat/dict/postion

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
            "name": "左"
        },
        {
            "code": 2,
            "name": "右"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.7 获取方向列表

* __Method__
  GET

* __URL__
  api/wechat/dict/direction

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
            "name": "伸展"
        },
        {
            "code": 2,
            "name": "屈曲"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.8 获取医院列表

* __Method__
  GET

* __URL__
  api/wechat/dict/hospitals

* __Request__

 Param | Type | Description

 filter | String | 过滤查询

* __Response__

> 成功

```
{
    "code": 0,
    "data": [
        {
            "code": 1,
            "name": "中国医院",
            "group": "Z"
        },
        {
            "code": 2,
            "name": "美国医院",
            "group": "M"
        },
        {
            "code": 3,
            "name": "非洲医院",
            "group": "F"
        },
        {
            "code": 4,
            "name": "日本医院",
            "group": "R"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.9 获取科室列表

* __Method__
  GET

* __URL__
  api/wechat/dict/departments

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
            "name": "呼吸内科"
        },
        {
            "code": 2,
            "name": "肾脏内科"
        },
        {
            "code": 3,
            "name": "眼科"
        },
        {
            "code": 4,
            "name": "耳科"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```


### 3.10 获取积分变化原因列表

* __Method__
  GET

* __URL__
  api/wechat/dict/scoreChange

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
            "name": "锻炼"
        },
        {
            "code": 2,
            "name": "兑换"
        },
        {
            "code": 3,
            "name": "分享"
        },
        {
            "code": 4,
            "name": "注册"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```

### 3.11 获取评估结论列表

* __Method__
  GET

* __URL__
  api/wechat/dict/appraisal

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
            "name": "维持"
        },
        {
            "code": 2,
            "name": "改善"
        },
        {
            "code": 3,
            "name": "恶化"
        },
        {
            "code": 4,
            "name": "脱落"
        }
    ]
}
```

> 失败

```
{"code":2,
"data":参数错误}
```





## 4  病例档案

### 4.1 创建病例档案

* __Method__
  POST

* __URL__
  api/wechat/ma

* __Request__

 Param | Type | Description

 patientId | Long | 病人id  当病人Id为null时，病人自己创建档案，不为null医生创建病历档案

> 请求参数Json格式

```
{ 
 "startTime":"", //开始时间 
 "illnessState":"1", //病情说明 
 "activity":"1", //活动因素不足 
 "process":"1", //病程 "
 department":"1", //科室 
 "hospital":"1",//医院 
 "doctorName":"主刀医生"  //主刀医生名字
  "content":"asdasdasd"  //病例描述


  "clinicalDataDtoList":"[
    { 
        indexType: -1, // 描述图片的索引值 
        type: '最近一次', 
        time: '2017-09-02',
        src: "",//这里传微信返回的图片id 
    },
    { 
      indexType: 2, // 描述图片的索引值 
      type: '术前',
      time: '2017-09-02',
      src: "",//这里传微信返回的图片id 
    }
  ]"  //上传图片列表

  "clinicalDataDtoList":"[
    { 
      "ClinicalDataDto1" : {
        indexType: -1, // 描述图片的索引值 
        type: '最近一次', 
        time: '2017-09-02',
        src: "",//这里传微信返回的图片id 
      }
    },
    { 
      "ClinicalDataDto2" : {
        indexType: 2, // 描述图片的索引值 
        type: '术前',
        time: '2017-09-02',
        src: "",//这里传微信返回的图片id 
      }
    }
  ]"  //上传图片列表

}
```

* __Response__

>成功

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

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


### 4.2  病例档案编辑图片

* __Method__
  PUT

* __URL__
  api/wechat/ma/img/{id}

* __Request__

 Param | Type | Description

 id | Long | 图片Id

 > 请求参数Json格式

 ```
 {
  "content":"", //图片描述
  "time":"2017816", //图片时间点
  "type":"1", //图标标签 1术前，2术后，3最近一次，4 最大变化
 
 }
 ```

* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```

### 4.3  病例档案删除单张图片

* __Method__
  DELETE

* __URL__
  api/wechat/ma/img/{id}


* __Request__

 Param | Type | Description

 id | Long | 图片Id


* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```

### 4.4  获取病历档案

* __Method__
  GET

* __URL__
  api/wechat/ma


* __Request__

 Param | Type | Description

 patientId | Long | 当病人id是null时候，是病人自己看病历档案，不为null的时候是医生通过这个病人id看他的病理档案


* __Response__

>成功

```
{
    "code": 0,
    "data": [
        {
            "id": 1,   //病历档案id
            "patientId": 1,  //病人id
            "surgeryTime": null,  //初诊时间
            "startTime": null,  //开始时间
            "illnessState": 1,  //病情说明对应的code
            "activity": 1,   //活动不足对应的code
            "process": 1,   //病程对应的code
            "department": null,   //科室对应的code
            "hospital": null,   //医院对应的code
            "doctorName": null,   //主刀医生名字
            "content": null,    //描述
            "createTime": 1502780406000,
            "updateTime": 1502780406000
            "clinicalDataDtoList":"[ 
   				  { 
              content : null
              createTime : 1506307271000
              file : null
              id : 1
              indexType : 0
              maId : 146
              responseEntity : Object
              src : "/clinicalImg/uoO2w-am16UdUyB_PmfKqwmROY5eH8ZJZTu1wXtSW9HCX7BQK4t9sxtkJf51Tdbw.jpg"
              time : 1506297600000
              type : "术前"
              updateTime : null
            {
              "id": 2,
              "patientId": 1,
              "surgeryTime": null,
              "startTime": null,
              "illnessState": 1,
              "activity": 1,
              "process": 1,
              "department": null,
              "hospital": null,
              "doctorName": null,
              "content": null,
              "createTime": 1502783195000,
              "updateTime": 1502783195000
              "clinicalDataDtoList":"[]
            }
    ]
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```



### 4.5  编辑病历档案

* __Method__
  PUT

* __URL__
  api/wechat/ma/{maId}

* __Request__

 Param | Type | Description

 maId | Long | 病理档案ID


> 请求参数Json格式

```
{

    "patientId": 1,
    "surgeryTime": null,
    "startTime": 1502780406000,
    "illnessState": 2,
    "activity": 2,
    "process": 2,
    "department": 4,
    "hospital": 4,
    "doctorName": null,
    "content": 123333,
    "clinicalDataDtoList":"[ 
   { indexType: -1, // 描述图片的索引值 
   type: '最近一次', 
   time: '2017-09-02',
    picture: "",//这里传微信返回的图片id 
    },
     { indexType: 2, // 描述图片的索引值 
     type: '术前',
      time: '2017-09-02',
       picture: "",//这里传微信返回的图片id 
       }
      ]"  //上传图片列表  //如果没有上传新的图片则传null
 }
```


* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```

### 4.6  删除病历档案

* __Method__
  DELETE

* __URL__
  api/wechat/ma/{maId}


* __Request__

 Param | Type | Description

 maId  | Long  | 病历档案Id


* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```






##  5 训练


### 5.1 创建训练计划

* __Method__
  POST

* __URL__
  api/wechat/exerciseProcess

* __Request__

 Param | Type | Description

 patientId  | Long  | 病人id   当病人id是null时候，是病人自己创建训练计划，不为null的时候是医生通过这个病人id创建训练计划
​    
 > 请求参数Json格式

 ```
 {
  "doctorId":"1",  //医生id
  "arthro":"1",   //关节
  "postion":"1",  //部位
  "direction":"1",  //方向
  "startTime":"1",  //开始时间
  "endTime":"1"  //结束时间
  "note":"这是一个训练计划"  //备注
 
 }
 ```

* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


### 5.2 通过医院选择医生

* __Method__
  GET

* __URL__
  api/wechat/user/doctor/{code}

* __Request__

 Param | Type | Description

 code | Integer | 医院的code


* __Response__

 >成功

 ```
{
    "code": 0,
    "data": [
             {
                   "doctorId": 1,  //医生id
                   "headPortrait": "",  //头像路径
                   "sex": 1,  //性别
                   "status": null,  //状态 1 未验证 2 已验证 3拒绝
                   "realName": "admin",  //医生名字
                   "userId": 1,  //用户id
                   "hospital": 1,  //医院code
                   "department": 1,  //科室
                   "professiona": "1",  //职称
                   "description": "1"  //描述
               },
               {
                   "doctorId": 2,
                   "headPortrait": null,
                   "sex": null,
                   "status": null,
                   "realName": "xxxx",
                   "userId": 2,
                   "hospital": 1,
                   "department": 1,
                   "professiona": "1",
                   "description": "1"
               },
               {
                   "doctorId": 3,
                   "headPortrait": null,
                   "sex": null,
                   "status": null,
                   "realName": "李会长",
                   "userId": 3,
                   "hospital": 1,
                   "department": null,
                   "professiona": null,
                   "description": null
               },
               {
                   "doctorId": 4,
                   "headPortrait": null,
                   "sex": null,
                   "status": null,
                   "realName": "xxxx",
                   "userId": 4,
                   "hospital": 1,
                   "department": null,
                   "professiona": null,
                   "description": null
               }
    ]
}
 ```

 >失败

 ```
 {
   "code":2,
   "data":"参数错误"
 }
 ```


 ### 5.3 病人看康复师对应的康复计划

* __Method__
   GET

* __URL__
    api/wechat/exerciseProcess

* __Request__

  Param | Type | Description

   doctorId | Long | 医生id


* __Response__

  >成功

  ```
  {
    "code": 0,
    "data": [
        {
            "id": 4,
            "patientId": 5, //病人id
            "doctorId": 1, //医生id
            "arthro": 1, //关节
            "postion": 1, //部位
            "direction": 1, //方向
            "startTime": 0, /开始日期
            "endTime": 0, //结束日期
            "createBy": 5,
            "updateBy": 5,
            "sign": null, //#医生有没有读到
            "report": 0, #0代表没有新的评估报告,1代表新的评估报告
            "record": 0, #0代表没有新的训练记录,1代表有新的训练记录
            "createTime": 1503815636000,
            "updateTime": 1503815636000,
            "note": "这是一个训练计划", //备注
            "realName": null,
            "overdue": 17405 //是否是历史康复计划
        },
        {
            "id": 5,
            "patientId": 5,
            "doctorId": 1,
            "arthro": 1,
            "postion": 1,
            "direction": 1,
            "startTime": 0,
            "endTime": 0,
            "createBy": 5,
            "updateBy": 5,
            "sign": null,
            "report": 0,
            "record": 0,
            "createTime": 1503815660000,
            "updateTime": 1503815660000,
            "note": "这是一个训练计划",
            "realName": null,
            "overdue": 17405
        },
        {
            "id": 6,
            "patientId": 5,
            "doctorId": 1,
            "arthro": 1,
            "postion": 1,
            "direction": 1,
            "startTime": 0,
            "endTime": 0,
            "createBy": 5,
            "updateBy": 5,
            "sign": null,
            "report": 0,
            "record": 0,
            "createTime": 1503815661000,
            "updateTime": 1503815661000,
            "note": "这是一个训练计划",
            "realName": null,
            "overdue": 17405
        }
    ]
  }
  ```

  >失败

  ```
  {
    "code":2,
    "data":"参数错误"
  }
  ```


   ### 5.4 康复师看病人的康复计划

*  __Method__
    GET

*  __URL__
     api/wechat/exerciseProcess/{patientId}

   * __Request__

   Param | Type | Description

   patientId | Long | 病人id


* __Response__

   >成功

  ```
  {
      "code": 0,
      "data": [
          {
              "id": 3,
              "patientId": 1, //病人id    
              "doctorId": 3, //医生id
              "arthro": 1, //关节
              "postion": 1, //部位
              "direction": 1 //方向
              "startTime": 0, //开始日期
              "endTime": 0, //结束日期
              "createBy": 1,
              "updateBy": 1,
              "sign": null, //判断医生有没有看到这条康复计划
              "report": 0, //0代表没有新的报告，1代表有新的评估报告
              "record": 0, //0代表没有新的训练记录，1代表有新的训练记录
              "createTime": 1503034173000,
              "updateTime": 1503034173000,
              "note": "这是一个训练计划"
          }
      ]
  }
  ```

    >失败

    ​```
    {
      "code":2,
      "data":"参数错误"
    }
    ​```


   ### 5.5 根据康复计划获取所有的康复师详细信息

* __Method__
  GET

* __URL__
  api/wechat/exerciseProcess/doctors

* __Request__

Param | Type | Description

无


* __Response__

>成功

```
{
"code": 0,
"data": [
    {
        "patientId": 1,
        "doctorId": 2,   //可以根据这个康复师id，查询对应的所有康复计划
        "doctorName": "xxxx", //医生名字
        "sex": null,
        "age": 1,  
        "brith": 1466737445000,
        "professiona": "1",  //职称
        "description": "1",  //描述
        "hospital": 1, //医生所在医院
        "department": 1, //科室
        "openId": "qwer",
        "isComfired": 1, //状态1为病人申请康复师 康复师已验证状态
        "headPortRait":""
    },
    {
        "patientId": 1,
        "doctorId": 3,
        "doctorName": "李会长",
        "sex": null,
        "age": null,
        "brith": null,
        "professiona": null,
        "description": null,
        "hospital": 1,
        "department": null,
        "openId": "openId",
        "isComfired": 1,
        "headPortRait":""
    }
]
}
```

>失败

```
{
"code":2,
"data":"参数错误"
}
```

### 5.6 更新训练计划

* __Method__
  PUT

* __URL__
  api/wechat/exerciseProcess

* __Request__

Param | Type | Description

epId  | Long  | 训练计划id   

> 请求参数Json格式

```
 {
  "doctorId":"1", //医生id
  "arthro":"1",  //关节
  "postion":"1",  //左右
  "direction":"1", // 方向
  "startTime":"1", //开始日期
  "endTime":"1"  ,//结束日期
  "note":"这是一个训练计划2"     //备注
 }
```

* __Response__

>成功

```
{
  "code": 0,
  "data": null
}
```

>失败

```
{
"code":2,
"data":"参数错误"
}
```


### 5.7 删除训练计划

* __Method__
  DELETE

* __URL__
  api/wechat/exerciseProcess/{epId}

* __Request__

   Param | Type | Description

   epId  | Long  | 训练计划id   


* __Response__

>成功

```
{
    "code": 0,
    "data": null
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


### 5.8 病人获取康复计划列表

* __Method__
  GET

* __URL__
  api/wechat/exerciseProcess/eps

* __Request__

   Param | Type | Description

   无


* __Response__

>成功

```
{
    "code": 0,
    "data": [
        {
            "id": 2,
            "patientId": 1,
            "doctorId": 1,
            "arthro": 1,
            "postion": 1,
            "direction": 1,
            "startTime": 0,
            "endTime": 0,
            "createBy": 1,
            "updateBy": 1,
            "sign": null,
          "report": 0, //0代表没有新的报告，1代表有新的评估报告
          "record": 0, //0代表没有新的训练记录，1代表有新的训练记录
            "createTime": 1503034169000,
            "updateTime": 1503561542000,
            "note": "这是一个训练计划",
            "isComfirmed":0, //0未验证，1、同意,-1、拒绝
            "comfirmedTime":1503561542000,//确认时间
            "realName": "admin",   //医生名字
            "overdue": 17403  , //不等于0就是康复计划过期，这代表超过康复计划结束时间的17403天
        },
        {
            "id": 3,
            "patientId": 1,
            "doctorId": 3,
            "arthro": 1,
            "postion": 1,
            "direction": 1,
            "startTime": 0,
            "endTime": 0,
            "createBy": 1,
            "updateBy": 1,
            "sign": null,
             "report": 0, //0代表没有新的报告，1代表有新的评估报告
              "record": 0, //0代表没有新的训练记录，1代表有新的训练记录
            "createTime": 1503034173000,
            "updateTime": 1503034173000,
            "note": "这是一个训练计划",
            "realName": "李会长",
            "overdue": 17403
        }
    ]
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


​    
​    
##  6 报告


### 6.1 创建报告

* __Method__
  POST

* __URL__
  api/wechat/report/{epId}

* __Request__

   Param | Type | Description

   epId | Long | 训练记录ID

> 请求参数Json格式

```
 {
  "startTime":"112321313",  //开始时间
  "endTime":"11232131355",  //结束时间
  "verdict":"1", //评估结论
  "content":"asdasdasd"  //内容
 
 }
```

* __Response__

>成功

```
{
"code": 0,
"data": null
}
```

>失败

```
{
"code":2,
"data":"参数错误"
}
```


​    
### 6.2 通过训练计划查询评估报告

* __Method__
  GET

* __URL__
  api/wechat/report/{epId}

* __Request__

  Param | Type | Description

  epId  | Long  | 训练计划id   

* __Response__

>成功

```
{
"code": 0,
"data": [
{
    "id": 1,
    "epId": 2, //康复计划id
    "doctorId": 23, //医生id
    "startTime": 112321000, //开始日期
    "endTime": 11232131000, //结束日期
    "verdict": 1, //评估结论
    "content": "asdasdasd", // 内容
    "createTime": null,
    "updateTime": null
},
{
    "id": 2,
    "epId": 2,
    "doctorId": 23,
    "startTime": 112321000,
    "endTime": 11232131000,
    "verdict": 1,
    "content": "asdasdasd",
    "createTime": null,
    "updateTime": null
}
]
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```

##  7 申请

### 7.1 创建申请,添加康复师

* __Method__
  POST

* __URL__
  api/wechat/application/{doctorId}

* __Request__

  Param | Type | Description

  doctorId  | Long  | 病人id 

* __Response__

>成功

```
{
    "code": 0,
    "data": {
        "id": 2,    //申请id
        "patientId": 5,
        "doctorId": 24,  //康复师id
        "status": 1,  //未验证
        "createtime": 1503376487000
    }
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


### 7.2 医生查询患者

* __Method__
  GET

* __URL__
  /api/wechat/exerciseProcess/getPatientByPatientId/{type}

* __Request__

  Param | Type | Description

  type | String  |type=0查询未验证状态的病人,type=1查询已同意状态的病人

  filter | String | 根据用户名模糊查询

* __Response__

>成功

```
{
    "code": 0,
    "data": [
        {
            "Id": 1,  //康复计划id
            "userId": 1,  //用户id
            "patientId": 1,   //病人id
            "doctorId": 24,  //医生id
            "arthro": "1", //关节
            "postion": "1", //部位
            "direction": "2", //方向
            "arthro": "我是一个康复计划", //康复计划描述
            "note": "我是一个康复计划", //康复计划描述
            "isComfirmed": 0,  //0未验证，1同意 ,-1拒绝 就查询不到了
            "userName": "admin",   //病人名字
            "startTime": 0,  //康复计划开始时间
            "endTime": 0,   //康复计划结束时间
            "birthDate": 0,   //年龄
             "sex": 0,   //性别
             "headPortrait":null //头像
            "createtime": 1503371915000
        }
    ]
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```


### 7.3 医生同意病人添加

*  __Method__
    GET

*  __URL__
    /api/wechat/exerciseProcess/updateIsComfirmed

   * __Request__

     Param | Type | Description

     epId| Long  |康复计划id

     isComfirmed| Long| 1代表同意 -1代表拒绝

   * __Response__

   >成功

   ```
   {
       "code": 0,
       "data": null
   }
   ```

   >失败

   ```
   {
     "code":2,
     "data":"参数错误"
   }
   ```


   ### 7.4 医生通过病人id查询病人信息

*  __Method__
   GET

*  __URL__
      api/wechat/user/{patientId}

   * __Request__

     Param | Type | Description

     patientId | Long  | 病人id


* __Response__

   >成功

   ```
   {
    "code": 0,
    "data": {
        "userId": 1, //用户id
        "patientId": 1, //病人id
        "openId": "admin", 
        "realName": "admin", 
        "sex": 1,
        "phone": "123456789", /手机号码
        "birthDate": 1281750648000, //生日
        "scoreValue": 14, //总积分
        "headPortrait": "", //头像
        "createTime": 1501725048000,
        "updateTime": 1501725048000,
        "age": 7
    }
   }
   ```

   >失败

   ```
   {
     "code":2,
     "data":"参数错误"
   }
   ```

## 8 用户积分和优惠券
### 8.1 用户注册后、锻炼后、分享后、调用此接口
* __Method__
  GET
* __URL__
  /api/wechat/score/createScore?scoreChange=1
* __Request__
  Param | Type | Description
  scoreChange | INTEGER  | 积分操作变化原因，如果是1(注册)、3(锻炼)、4(分享)、则不用传优惠券ID
* __Response__
>成功

```
{
  "code": 0,
  "data": {}
}
```

>失败

```
{
  "code":2,
  "data":"参数错误"
}
```
### 8.2 用户查询优惠券的总数量

* __Method__
  GET

* __URL__
  /api/wechat/score/getCouponListByUserId
  如果用户有优惠券则获取此接口返回的优惠券对象，前端获取返回集合的length，如果为null 则前端判断该用户优惠券为0张
* __Request__

  Param | Type | Description
  无

* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": null,
      "userId": null, //用户id
      "couponId": null, //优惠券id
      "status": null, //是否使用（该字段暂时不用）
      "createTime": 1503454727000,
      "updateTime": null,
      "redeemScore": 10,//兑换的积分值
      "couponMoney": 100, //优惠券金额
      "redeemScores": null,//后端处理过的数据
      "couponMoneys": null, //后端处理过的数据
      "validity": 1503540991000 //过期时间
    }
  ]
}
```

>失败

```
{
  "code":0,
  "data":null
}
```
### 8.3 用户查询积分明细

* __Method__
  GET

* __URL__
  /api/wechat/score/getScoreDetail
* __Request__

  Param | Type | Description
  无

* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": 9,
      "userId": 21,
      "scoreChange": 1,  //积分操作
      "changeValue": "+1", //变化积分
      "surplusScore": 9, //剩余积分
      "createTime": 1503454386000,
      "updateTime": null
    },
    {
      "id": 11,
      "userId": 21,
      "scoreChange": 2,
      "changeValue": "-10",
      "surplusScore": 0,
      "createTime": 1503454727000,
      "updateTime": null
    }
  ]
}
```

>失败

```
{
  "code":0,
  "data":null
}
```

### 8.4 查看用户已兑换且没有过期的优惠券列表

* __Method__
  GET

* __URL__
  /api/wechat/score/getCouponListByUserId
* __Request__

  Param | Type | Description
  无

* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": null,
      "userId": null,
      "couponId": null,
      "status": null,
      "createTime": 1503454727000,
      "updateTime": null,
      "redeemScore": 10,
      "couponMoney": 100,
      "redeemScores": null,
      "couponMoneys": null,
      "validity": 1503540991000
    }
  ]
}
```
>失败
```
{
  "code":0,
  "data":null
}
```

### 8.5 查看用户历史优惠券列表

* __Method__
  GET

* __URL__
  /api/wechat/score/getHistoryCouponList
* __Request__

  Param | Type | Description
  无

* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": null,
      "userId": null,
      "couponId": null,
      "status": null,
      "createTime": 1503454727000,
      "updateTime": null,
      "redeemScore": 10,
      "couponMoney": 100,
      "redeemScores": null,
      "couponMoneys": null,
      "validity": 1503368191000
    }
  ]
}
```
>失败
```
{
  "code":0,
  "data":null
}
```

### 8.6 查看所有没有过期且能够兑换的优惠券列表

* __Method__
  GET

* __URL__
  /api/wechat/score/getCouponList
* __Request__

  Param | Type | Description
  无

* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": 2,
      "couponPassword": "123456",
      "redeemScore": 100,
      "couponCount": 200,
      "couponMoney": 200,
      "validity": 1504145819000,
      "createTime": 1503454622000,
      "updateTime": 1503454622000
    }
  ]
}
```
>失败
```
{
  "code":0,
  "data":null
}
```

### 8.7 用户兑换优惠券

* __Method__
  GET

* __URL__
  /api/wechat/score/createScore
* __Request__

  Param | Type | Description
  scoreChange Integer 积分操作变化原因必须传2
   couponId   Long   优惠券Id  必须传 
* __Response__

>成功

```
{
  "code": 0,
  "data": {
    "result": "兑换成功"
  }
}
```
>失败
```
{
  "code": 0,
  "data": {
    "result": "你的积分不足以兑换该优惠券"
  }
}
```
### 8.8 兑换记录

* __Method__
  GET
* __URL__
  /api/wechat/score/getChangeRecordList
* __Request__
  Param | Type | Description
   无
* __Response__

>成功

```
{
  "code": 0,
  "data": [
    {
      "id": null,
      "userId": null,
      "couponId": null,
      "status": null,
      "createTime": 1503393013000,
      "updateTime": null,
      "redeemScore": 6,
      "couponMoney": 12312.1,
      "redeemScores": "-6",
      "couponMoneys": "兑换12312.1元优惠券",
      "validity": null
    }
  ]
}
```
>失败
```
{
  "code": 0,
  "data": []
}
```
# 9 账户设置-修改手机号
### 9.1 判断原手机的验证码是否正确

* __Method__
  GET

* __URL__
  /api/wechat/user/vaildCaptcha
* __Request__
  Param | Type | Description
  captcha String  验证码 
* __Response__
>成功
```
{
  "code": 0,
  "data": {
    "result": "0"
  }
}
```
>失败
```
{
  "code": 0,
  "data": {
    "result": "验证码错误"
  }
}
```
### 9.2 修改手机号

* __Method__
  GET
* __URL__
  /api/wechat/user/updatePhone
* __Request__
  Param | Type | Description
  phone   String  手机号
  captcha String  验证码 
* __Response__
>成功
```
{
  "code": 0,
  "data": {
    "result": "修改成功"
  }
}
```
>失败
```
{
  "code": 0,
  "data": {
    "result": "验证码错误"
  }
}
{
  "code": 0,
  "data": {
    "result": "手机号已存在"
  }
}
```

# 10 我的设备
### 10.1 查看我的设备
* __Method__
  POST
* __URL__
  /api/wechat/device/queryDevice
* __Request__
  Param | Type | Description
   无
* __Response__
>成功
```
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "userId": 29,  //用户id
      "model": "1", //设备型号
      "sn": "1", //设备序列号
      "codeUrl": null, //设备二维码
      "config": "你是左手康复2号", //产品配置信息
      "life_status": null, //产品生命周期
      "type": 1, //设备类型
      "produce_date": null, //生产日期
      "activate_date": null, //激活日期
      "scrap_date": null, //报废日期
      "create_time": null,
      "update_time": null
    },
    {
      "id": 2,
      "userId": 29,
      "model": "1",
      "sn": "1",
      "codeUrl": null,
      "config": "我是右手康复一号",
      "life_status": null,
      "type": 1,
      "produce_date": null,
      "activate_date": null,
      "scrap_date": null,
      "create_time": null,
      "update_time": null
    }
  ]
}
```
>失败
```
{
  "code": 0,
  "data": {
    "result": ""
  }
}
```
### 10.2 解除设备绑定
* __Method__
  POST
* __URL__
  /api/wechat/device/removeDevice
* __Request__ 
  Param | Type | Description
   deviceId Long   设备ID 
* __Response__ 
>成功
```
{
  "code": 0,
  "data": null
}
```
>失败
```
   {
     "code":2,
     "data":"参数错误"
   }
```

# 11 训练记录数据
### 10.1 根据康复计划Id显示主页第一条最新的康复记录数据
* __Method__
  GET
* __URL__
  /api/wechat/exerciseRecord/getExerciseRecordByepId
* __Request__
  Param | Type | Description
  epId     Long    康复计划Id
* __Response__
>成功
```
{
  "code": 0,
  "data": [
    {
      "id": 39,
      "patientId": 1, //病人Id
      "deviceId": 1, //设备Id
      "arthro": 1, //关节
      "postion": 1,//部位
      "direction": 1,//方向
      "geolocation": "上海虹口医院", //医院名称
      "startTime": 1506996331000, //训练开始时间
      "endTime": 1506999934000,//训练结束时间
      "time": null,
      "startAngle": 45, //开始角度
      "endAngle": 60,//结束角度
      "sr": 4, //sr次数
      "createTime": 1507002934000,//创建时间
      "recordData": "[{'time':'2017-10-03 11:00:00','angle':'45','level':'1'},//图表数据
      {'time':'2017-10-03 11:05:34','angle':'48','level':'1'},{'time':'2017-10-03 11:08:34','angle':'45','level':'1'},{'time':'2017-10-03 11:15:34','angle':'50','level':'1'},{'time':'2017-10-03 11:30:34','angle':'48','level':'2'},{'time':'2017-10-03 11:33:34','angle':'50','level':'2'},{'time':'2017-10-03 11:43:34','angle':'55','level':'2'},{'time':'2017-10-03 11:50:34','angle':'60','level':'2'},{'time':'2017-10-03 11:55:00','event':'end'}]",
      "trainingLength": 0,//训练时长
      "trainingLengthDesc": null,//训练时长 数据整合
      "angleChange": 0,//变化角度
      "maximumTime": 0,//最长时间
      "minimumTime": 0,//最短时间
      "averageTime": 0,//平均时间
    }
  ]
}
```
>失败
```
{
  "code": 0,
  "data": null
}
```
### 10.2 通过康复计划Id获取周、月训练记录
* __接口使用说明__
  进入界面默认显示最近一周的康复记录数据，并只要传入参数 康复计划Id,如果需要显示下周或者上周的数据则需要传入开始和结束时间、康复计划Id
* __Method__
  GET
* __URL__
  /api/wechat/exerciseRecord/getExerciseRecordWeekMonth
* __Request__ 
  Param | Type | Description
  epId    Long   康复计划ID 
  startTime Long  开始时间戳
  endTime  Long  结束时间戳
* __Response__ 
>成功
```
{
  "code": 0,
  "data": [
    {
      "startAngle": 60,//开始角度
      "endAngle": 85, //结束角度
      "time": "2017-10-03 11:00:00" //训练时间
    },
  ]
}
```
>失败
```
   {
     "code":2,
     "data:null
   }
```
### 10.3 通过康复计划id和开始结束时间得到每天的训练记录
* __接口使用说明__
  进入界面默认显示最近一周的康复记录数据，并只要传入参数 康复计划Id,如果需要显示下周或者上周的数据则需要传入开始和结束时间、康复计划Id
* __Method__
  GET
* __URL__
  /api/wechat/exerciseRecord/getExerciseRecordDayByTime
* __Request__ 
  Param | Type | Description
  epId    Long   康复计划ID 
  startTime Long  开始时间戳
  endTime  Long  结束时间戳
* __Response__ 
>成功
```
{
  "code": 0,
  "data": [
    {
      "id": null,
      "patientId": null,
      "deviceId": null,
      "arthro": null,
      "postion": null,
      "direction": null,
      "geolocation": null,
      "startTime": null,
      "endTime": null,
      "time": null,
      "startAngle": 0,
      "endAngle": 0,
      "sr": 4,// sr次数
      "createTime": 1507002934000,//日期
      "recordData": null,
      "trainingLength": 0,
      "trainingLengthDesc": "训练时长55m", //训练时长
      "angleChange": 15,//角度变化
      "maximumTime": 0,
      "minimumTime": 0,
      "averageTime": 0,
      "dataDtoList": null
    }]
 }
```
>失败
```
   {
     "code":2,
     "data:null
   }
```

### 10.4 通过康复记录Id获取单次训练记录明细
* __Method__
  GET
* __URL__
  /api/wechat/exerciseRecord/getExerciseRecordDetail
* __Request__ 
  Param | Type | Description
  erId    Long   康复记录ID 
* __Response__ 
>成功
```
{
  "code": 0,
  "data": {
    "id": 1,
    "patientId": 29,
    "deviceId": 1,
    "arthro": 1,
    "postion": 1,
    "direction": 1,
    "geolocation": "上海虹口医院",
    "startTime": 1504145131000,
    "endTime": 1504148734000,
    "time": null,
    "startAngle": 50,//开始角度
    "endAngle": 105,//结束角度
    "sr": 6,//sr 次数
    "createTime": 1504058762000,
    "recordData": "[{'time':'2017-08-31 11:00:00','angle':'50','level':'1'},//图表数据
    {'time':'2017-08-31 11:05:34','angle':'54','level':'1'},{'time':'2017-08-31 11:08:34','angle':'60','level':'1'},{'time':'2017-08-31 11:15:34','angle':'68','level':'1'},{'time':'2017-08-31 11:30:34','angle':'63','level':'2'},{'time':'2017-08-31 11:33:34','angle':'89','level':'2'},{'time':'2017-08-31 11:43:34','angle':'95','level':'2'},{'time':'2017-08-31 11:50:34','angle':'105','level':'2'},{'time':'2017-08-31 11:55:00','event':'end'}]",
    "trainingLength": 55,//训练时长
    "trainingLengthDesc": null,
    "angleChange": 55,//角度变化
    "maximumTime": 15,//最长时间
    "minimumTime": 4,//最短时间
    "averageTime": 8,//平均时间
  }
}
```
>失败
```
   {
     "code":2,
     "data:null
   }
```



 
