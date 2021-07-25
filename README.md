# Contributors

## 機能概要

以下２つの機能を作成

-   architecture-components-samples の Contributos を一覧表示
-   Contributos 一覧の各 item タップ時に、タップされた Contributor の詳細情報を表示する画面

[architecture-components-samples](https://github.com/android/architecture-components-samples)はこちらとなる。

### Contributos 一覧画面

下記 API を実行し、取得した情報を RecycleView にて一覧画面で表示  
https://api.github.com/repositories/90792131/contributors

<img src="https://raw.githubusercontent.com/dYoshitakaKato/Contributors/main/images/Screenshot_20210726-033131_Contributors.jpg" width="200">

### Contributor 詳細画面

一覧画面タップ時に、選択された Contributors の login 情報を元に  
下記 API を実行し、取得した情報を画面へ表示  
https://api.github.com/users/{login}

<img src="https://raw.githubusercontent.com/dYoshitakaKato/Contributors/main/images/Screenshot_20210726-033126_Contributors.jpg" width="200">

## パッケージ構成

app/src/main/java/com/example/contributors 配下のパッケージ構成は下記となる。

-   activity : ~Activity を定義する
-   adapter : ~Aadapter を定義する
-   fragments : ~Fragments を定義する
-   model : ~各種 data 方を定義する
-   repository : ~データを取得方法が定義する
-   util : ~どの画面でも用いる汎用的なものが定義する
-   viewModel : ~ViewModel を定義する

```
├── AndroidManifest.xml
├── java
│   └── com
│       └── example
│           └── contributors
│               ├── ContributorsApplication.kt
│               ├── activity
│               │   └── MainActivity.kt
│               ├── adapter
│               │   └── ContributorsAdapter.kt
│               ├── fragments
│               │   ├── DetailFragment.kt
│               │   └── MainFragment.kt
│               ├── model
│               │   ├── Contributor.kt
│               │   └── ContributorDetail.kt
│               ├── repository
│               │   ├── ContributorRepository.kt
│               │   └── ContributorService.kt
│               ├── util
│               │   ├── Event.kt
│               │   ├── ImageUtil.kt
│               │   └── VisibleUtil.kt
│               └── viewModel
│                   ├── DetailViewModel.kt
│                   └── MainViewModel.kt
```
