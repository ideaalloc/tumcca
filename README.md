# Tumcca

What is the name for? Inspired by Tumblr, I suggest we name our Chinese Calligraphy and Arts platform as tumcca, while Tumblr is abbreviated for "tumblelogs".

### Running The Application

To test the example application run the following commands.

* To package the example run.

        mvn package

* To run the server run.

        java -jar target/tumcca-0.1.0-SNAPSHOT.jar server tumcca.yml

* To run in background.

        nohup java -jar tumcca-0.1.0-SNAPSHOT.jar server tumcca.yml >console.log 2>&1 &

### API


```
    POST    /api/sign-in
            Content-Type: application/json
            REQUEST: {"username": "wuwenchuan", "password": "888888"}
            RESPONSE:
            {
              "credit" : 100,
              "shopId" : 1,
              "credentials" : "6ac647dd-93f0-4fea-9b79-73e3dde6d180",
              "shopName" : "七彩马科技"
            }
            ERROR:
            403
            User or password incorrect
```
```
    POST    /api/sign-out
            Content-Type: application/json
            Authorization: Bearer 1975340b-0935-4c41-9b38-82e3eadda6f0
            REQUEST: Nil
            RESPONSE: Nil
            ERROR: Credentials are required to access this resource.
```
```
    POST    /api/sign-up
            Content-Type: application/json
            REQUEST:
            {
              "shopId": 1,
              "username": "wuwenchuan",
              "password": "888888",
              "credit": 100
            }
            RESPONSE: {"userId": 1}
```
```
    GET     /api/car-brands
            Content-Type: application/json
            Authorization: Bearer 1975340b-0935-4c41-9b38-82e3eadda6f0
            REQUEST: Nil
            RESPONSE:
            [{
              "key" : 1,
              "value" : "奥迪"
            },
            {
              "key" : 2,
              "value" : "阿尔法罗米欧"
            },
            {
              "key" : 3,
              "value" : "阿斯顿马丁"
            },
            ...]
            ERROR: Credentials are required to access this resource.
```
```
    GET     /api/car-models/:brandId eg: /api/car-models/1
            Content-Type: application/json
            Authorization: Bearer 1975340b-0935-4c41-9b38-82e3eadda6f0
            RESPONSE:
            [
                {
                  "key" : 149,
                  "value" : "奥迪A5(进口)"
                },
                {
                  "key" : 150,
                  "value" : "奥迪A6"
                },
                {
                  "key" : 152,
                  "value" : "奥迪Q7(进口)"
                },
                ...
              ]
            ERROR: Credentials are required to access this resource.
```
```
    GET     /api/car-versions/:modelId eg: /api/car-versions/149
            Content-Type: application/json
            Authorization: Bearer 1975340b-0935-4c41-9b38-82e3eadda6f0
            RESPONSE:
            [
                {
                  "key" : 6197,
                  "value" : "2012款奥迪A5敞篷 2.0TFSI Cabriolet"
                },
                {
                  "key" : 6198,
                  "value" : "2012款奥迪A5敞篷 2.0TFSI Cabriolet quattro"
                },
                {
                  "key" : 6200,
                  "value" : "2013款奥迪A5敞篷 40TFSI"
                },
                ...
              ]
            ERROR: Credentials are required to access this resource.
```
```
    POST    /auth/sign-up
            Content-Type: application/json
            REQUEST:
            {
              "username": "zhangwei",
              "password": "888888",
              "status": true
            }
            RESPONSE: {"username": "zhangwei"}
```
```
    POST    /auth/sign-in
            Content-Type: application/json
            REQUEST: {"username": "zhangwei", "password": "888888"}
            RESPONSE: {"sessionId": "e0f50e53-b4a5-40e2-abfc-cf0069a00737"}
            ERROR: No such user
```
```
    POST    /auth/sign-out
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            REQUEST: Nil
            RESPONSE: Nil
            ERROR: Credentials are required to access this resource.
```
```
    POST    /api/car-basic-info
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            REQUEST:
            {
              "carId": "鄂A J72220",
              "ownerName": "李宝华",
              "ownerPhone": "13871378890",
              "versionId": 6197,
              "mileage": 8968.9,
              "transferTimes": 0,
              "regDate": "2013-05-30"
            }
            RESPONSE: {"regId": 2}
            ERROR: Credentials are required to access this resource.
            ERROR: Car info insertion error
```
```
    GET     /api/car-basic-info/{regId} eg: /api/car-basic-info/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE:
            {
              "regDate" : "2013-05-31",
              "regId" : 1,
              "carId" : "鄂A J72221",
              "ownerPhone" : "13871378800",
              "ownerName" : "李金华",
              "mileage" : 8978.9,
              "versionId" : 6198,
              "transferTimes" : 1
            }
            ERROR: Credentials are required to access this resource.
            ERROR: 404 Car info not found
```
```
    PUT     /api/car-basic-info/{regId} eg: /api/car-basic-info/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            REQUEST:
            {
              "carId": "鄂A J72221",
              "ownerName": "李金华",
              "ownerPhone": "13871378800",
              "versionId": 6198,
              "mileage": 8978.9,
              "transferTimes": 1,
              "regDate": "2013-05-31"
            }
            RESPONSE: Nil
            ERROR: Credentials are required to access this resource.
            ERROR: Reg id not found, cannot process update
```
```
    POST    /api/detection-basic-info
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            REQUEST:
            {
              "regId": 1,
              "accidentEliminationDetection": "事故排除检测结果",
              "appearanceDetection": "外观检测结果",
              "interiorDetection": "内饰检测结果",
              "electricalEquipmentDetection": "电气设备检测结果",
              "engineDetection": "发动机检测结果",
              "roadTest": "路试检测结果是很拉风",
              "expectedPrice": 12.88,
              "minPrice": 10.88,
              "maxPrice": 13.88
            }
            RESPONSE: Nil
            ERROR: Credentials are required to access this resource.
            ERROR: There is no reg id registered in the car basic info, please register basic info first
```
```
    PUT     /api/detection-basic-info/{regId} eg: /api/detection-basic-info/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            REQUEST:
            {
              "accidentEliminationDetection": "事故排除检测结果是很拉风",
              "appearanceDetection": "外观检测结果是很拉风",
              "interiorDetection": "内饰检测结果是很拉风",
              "electricalEquipmentDetection": "电气设备检测结果是很拉风",
              "engineDetection": "发动机检测结果是很拉风",
              "roadTest": "路试检测结果是很拉风是很拉风",
              "expectedPrice": 12.99,
              "minPrice": 9.99,
              "maxPrice": 13.89
            }
            RESPONSE: Nil
            ERROR: Credentials are required to access this resource.
            ERROR: 404 Reg id not found, cannot process update
```
```
    GET     /api/detection-basic-info/{regId} eg: /api/detection-basic-info/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE:
            {
              "engineDetection" : "发动机检测结果",
              "regId" : 2,
              "accidentEliminationDetection" : "事故排除检测结果",
              "interiorDetection" : "内饰检测结果",
              "roadTest" : "路试检测结果是很拉风",
              "electricalEquipmentDetection" : "电气设备检测结果",
              "appearanceDetection" : "外观检测结果",
              "maxPrice" : 13.88,
              "expectedPrice" : 12.88,
              "minPrice" : 10.88
            }
            ERROR: Credentials are required to access this resource.
            ERROR: 404 Car detection not found error
```
```
    POST    /api/upload-photo/{regId} eg: /api/upload-photo/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            Payload: carPhoto: 2d67536a990d00000003e0000f002774
            RESPONSE:
            {
              "regId" : 1,
              "picId" : 1
            }
            ERROR: Credentials are required to access this resource.
```
```
    GET     /api/get-photo/{photoId} eg: /api/get-photo/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE: file
            ERROR: Credentials are required to access this resource.
            ERROR: Id Not found
```
```
    DELETE  /api/delete-photo/{photoId} eg: /api/delete-photo/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE: 1
            ERROR: Credentials are required to access this resource.
```
```
    GET     /api/get-photo-ids/{regId} eg: /api/get-photo-ids/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE:
            {"photoIds": [1, 2]}
            ERROR: Credentials are required to access this resource.
```
```
    POST    /api/inform-review/{regId} eg: /api/inform-review/1
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE: 1
            ERROR: Credentials are required to access this resource.
```
```
    GET     /api/review-result/{regId} eg: /api/review-result/9
            Content-Type: application/json
            Authorization: Bearer e0f50e53-b4a5-40e2-abfc-cf0069a00737
            RESPONSE:
            {
                regId: 9,
                process: "PASSED",
                message: "UNDER REVIEW",
                integral: 122220,
                auditor: "lvchao",
                createTime: "2015-03-25"
            }
            ERROR: Credentials are required to access this resource.
```
```
    WebSocket:
    http://localhost.com:9080/ws/review

    Android Client Reference:
    https://jfarcand.wordpress.com/2013/04/04/wasync-websockets-with-fallbacks-transports-for-android-node-js-and-atmosphere/
```
