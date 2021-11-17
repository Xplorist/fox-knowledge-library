---
title: React中条件渲染
date: 2021-01-28 15:28:00
categories: React
toc: true
---

# React中条件渲染

## 参考地址

> * [条件渲染](https://zh-hans.reactjs.org/docs/conditional-rendering.html)

## 代码示例

``` javascript
{boxItem.logoPic !== null &&
  <img src={'/portal/fileDownload?fileSavePath=' + encodeURIComponent(boxItem.logoPic.fileSavePath) + '&fileSaveName=' + encodeURIComponent(boxItem.logoPic.fileSaveName) + '&fileOriginName=' + encodeURIComponent(boxItem.logoPic.fileOriginName)} className={styles.icon}></img>
}
{boxItem.logoPic === null &&
  <img src={boxItem.picSrc || DefaultIcon} className={styles.icon} />
}
```

## 代码解释

之所以能这样做，是因为在 JavaScript 中，true && expression 总是会返回 expression, 而 false && expression 总是会返回 false。

因此，如果条件是 true，&& 右侧的元素就会被渲染，如果是 false，React 会忽略并跳过它。

请注意，返回 false 的表达式会使 && 后面的元素被跳过，但会返回 false 表达式。在下面示例中，render 方法的返回值是 <div>0</div>。