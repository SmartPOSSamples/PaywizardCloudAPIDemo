# PaywizardCloudAPIDemo
Reference for PayWizard Cloud API integration testing
Operating steps:
(1)RegisterTerminal
1)Update the params(JWT_TOKEN、CLIENT_ID、MID、MERCH_NAME、CLIENT_SECRET) to your params from email at com.cloudpos.demo.paywizard.http.HttpHelper.class
2)Run the project at one android device,such as Wizarpos Q2P.
3)Enter "pos id",The input pos id must be unique and serve as a unique identifier for the host.
4)Enter "terminal sn", which is the sn number of the wizarpos upt connection.
5)Click "registerTerminal",bind the host computer and UPT in the PayWizard backend.
6)Click "queryTerminal" to check the device binding list under the current account.
7)If need unbind Device,Click "unbindTerminal".
(2)Do Transcation
1)Enter "trans amount".
2)Enter "tradeNo",must ensure uniqueness.
3)Select transType,such as Purchase.
4)Click "start trans".
5)UPT will receive a payment message and be redirected to the payment page to complete the payment.
6)Click "Query Result" to query trans result,if transResult=0,the transcation is success.
7）If the original transaction was a PreAuth, and you need to complete the pre-authorization process. Please fill in the original transaction information (starting with "Ori").