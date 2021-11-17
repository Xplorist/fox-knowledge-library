# Vue纵向合并单元格demo

* 核心机制：&lt;template&gt;标签，v-if="index === 0", v-bind:rowspan="x.empList.length"

* html

``` html
<table class="table table-bordered">
    <tbody>
        <template v-for="x in tableData">
            <tr v-for="(y, index) in x.empList">
                <td v-if="index === 0" v-bind:rowspan="x.empList.length">{{ x.dept_name }}</td>
                <td>{{ y.person_name }}</td>
                <td>{{ y.account_name }}</td>
                <td>{{ y.email }}</td>
                <td>{{ y.extension }}</td>
                <td>{{ y.shortphone }}</td>
                <td>{{ y.departmentname }}</td>
            </tr>
        </template>
    </tbody>
    <thead>
        <tr>
            <th>专案角色</th>
            <th>姓名</th>
            <th>工号</th>
            <th>邮箱</th>
            <th>分机</th>
            <th>短号</th>
            <th>部门</th>
        </tr>
    </thead>
</table>
```

