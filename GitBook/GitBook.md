# GitBook

## 参考网址：

> GitBook官網地址：https://www.gitbook.com/
>
> GitBook介紹網址：[https://chrisniael.gitbooks.io/gitbook-documentation/content/](https://chrisniael.gitbooks.io/gitbook-documentation/content/)
> 
> [在内网通过GitBook打造电子书](https://blog.csdn.net/sinat_33087001/article/details/102019613)
> 
> [gitbook入门](https://www.jianshu.com/p/dc53e589897a)
> 
> [GitBook 使用教程](https://www.jianshu.com/p/421cc442f06c)
> 
> [使用 GitBook + GitLab 团队文档协作](https://www.jianshu.com/p/e74dad6845d1)
>
> * [Gitbook build stopped to work in node 12.18.3](https://github.com/GitbookIO/gitbook-cli/issues/110)

## 个人理解：

自己對GitBook的理解：GitBook的核心就是講MarkDown文件編譯成靜態HTML，同樣是基於NodeJs, 可對比Vue cli項目， GitBook的內部原理和Vue Cli的原理很相似。

## 安装：

``` 
npm install gitbook-cli -g
```

## 核心操作：

  1. gitbook init 初始化項目，編輯SUMMARY.md（編譯後生成目錄）和README.md(編譯後生成主頁)

  2. 編輯文檔內容

  3. gitbook serve 預覽生成的書籍

  4. gitbook build 組建打包生成靜態HTML項目

  5. 將生成靜態HTML項目部署到Nginx服務器上，這樣就生成了一本在線書籍了
