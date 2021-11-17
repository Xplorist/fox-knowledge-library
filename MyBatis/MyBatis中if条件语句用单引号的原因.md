# MyBatis中if条件语句用单引号的原因

* 原因如下：

> * 因为&lt;if test='publicFlag == "A"'&gt;中字符串"A",如果用'A'就会认为是字符A（char类型）

> * 因此，只要if条件判断中涉及到字符串，test='',test语句内容用单引号