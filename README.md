<p align="center"><a href="https://www.verygoodsecurity.com/"><img src="https://avatars0.githubusercontent.com/u/17788525" width="128" alt="VGS Logo"></a></p>
<p align="center"><b>vgs-android-sample</b></p>
<p align="center"><i>Sample of using VGS on Android with three common use cases.</i></p>

## Quick Start

Download the [latest APK](apk/vgs-sample-1.0.apk) and try it out.

## Use Cases

The app demonstrates three different use cases of using VGS. Sample is configured to work with [Httpbin](https://httpbin.verygoodsecurity.io/) echo server which should replaced with your API in real case scenario.
Data that should be secured via VGS is getting replaced with tokens containing prefix `tok_` on proxy response.

<img src="images/sample-use-cases.png" width="256" alt="Sample use cases">

1. Securing PII data:

<img src="images/sample-pii-data.png" width="256" alt="Sample PII data">

2. Securing credit card data:

<img src="images/sample-card-data.png" width="256" alt="Sample credit card data">

3. Securing bank account data:

<img src="images/sample-bank-data.png" width="256" alt="Sample bank account data">

## VGS Dashboard configuration

In order to configure the app to work with your vault apply the following route configuration via [VGS Dashboard](https://dashboard.verygoodsecurity.com/).

<img src="images/vgs-dashboard-routes.png" width="768" alt="Route configurations">

Upstream host configuration points to the [Httpbin](https://httpbin.verygoodsecurity.io/) echo server.

<img src="images/vgs-dashboard-upstream.png" width="512" alt="Persistent storage">

Most of the secured data should be saved using `PERSISTENT` storage and is configured using JsonPath.

<img src="images/vgs-dashboard-route-persistent.png" width="512" alt="Persistent storage">

Please note that CVV data should be configured to use `VOLATILE` storage due to PCI DSS requirements.

<img src="images/vgs-dashboard-route-volatile.png" width="512" alt="Volatile storage">

## Usage

To use the sample with your own vault change `vgs_proxy_url` in [strings.xml](https://github.com/verygoodsecurity/vgs-android-sample/blob/master/app/src/main/res/values/strings.xml#L7) file to the URL of your inbound route.
This value can be found on dashboard in `Vault URLs` as `Inbound Route URL`:

<img src="images/vgs-inbound-url.png" width="512" alt="Inbound Route URL">

## What is VGS?

_**Want to just jump right in?** Check out our [getting started
guide](https://www.verygoodsecurity.com/docs/getting-started)._

Very Good Security (VGS) allows you to enhance your security standing while
maintaining the utility of your data internally and with third-parties. As an
added benefit, we accelerate your compliance certification process and help you
quickly obtain security-related compliances that stand between you and your
market opportunities.

To learn more, visit us at https://www.verygoodsecurity.com/

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file
for details.
