### windows中按键脚本AutoHotkey

> 参考网址：
>
> https://sspai.com/post/43305
>
> 官网：https://www.autohotkey.com/
>
> 中文说明：https://wyagd001.github.io/zh-cn/docs/AutoHotkey.htm

***

test.ahk

```ahk
#z:: ;点击win+z键，开始这个脚本
loop
{
    send a ;点击a键
    sleep 1000 ;暂停1000毫秒（1秒）
} 
return

#x::Pause ;点击win+x键，暂停这个脚本
#c::ExitApp ;点击win+c键，退出这个脚本
```

***

按键脚本安装和使用说明.txt

```txt
首先安装AutoHotkey_1.1.31.00_setup.exe这个软件，下一步下一步就ok了。
然后直接双击test.ahk这个脚本，win+z键开启这个脚本，win+x键暂停脚本，win+c键退出脚本。

编辑脚本，选中test.ahk右键选择打开方式，选择记事本，就可以编辑脚本源码了。

代码非常简单，
loop
{
    send a ;点击a键
    sleep 1000 ;暂停1000毫秒（1秒）
} 

把a改成你需要改的按键
把1000改成你需要的延迟时间

更多说明文档，请参考以下网站：
https://wyagd001.github.io/zh-cn/docs/AutoHotkey.htm
```

