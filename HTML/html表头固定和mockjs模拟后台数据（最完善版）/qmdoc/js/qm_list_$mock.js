Mock.setup({
	timeout: 200
})

Mock.mock('query.x', {
	// 属性 list 的值是一个数组，其中含有 1 到 10 个元素
	    'list|100': [{
	        // 属性 id 是一个自增数，起始值为 1，每次增 1
			'file_origin_name|100': '',
			'factory|100': '',
			'project|10': '',
			'version|10': '',
			'memo|10': '',
			'creator|10': '',
			'create_date|10': '',
			'update_date|10': ''
	    }],
		'row_total|100': 1,
		'page_total|20': 1,
})