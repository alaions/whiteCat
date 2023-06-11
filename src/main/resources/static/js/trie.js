const searchInput = document.getElementById('msg');
const searchResults = document.querySelector('.search-results2');

searchInput.addEventListener('input', function() {
    if (this.value.trim() !== '') {
        // 向服务器请求搜索结果
        fetch(`/chen/searchTopic/${this.value}`)
            .then(response => response.json())
            .then(data => {
                // 清空搜索结果
                searchResults.innerHTML = '';

                // 遍历搜索结果，将其添加至下拉菜单中
                for (const result of data) {
                    const li = document.createElement('li');
                    li.innerText = result;
                    li.addEventListener('click', function() {
                        // 选中搜索结果，将其赋值给搜索框
                        searchInput.value = this.innerText;
                        // 清空搜索结果
                        searchResults.innerHTML = '';
                        selectName()
                    });
                    searchResults.appendChild(li);
                }

                // 显示下拉菜单
                searchResults.style.display = 'block';
            })
            .catch(error => console.error(error));
    } else {
        // 清空搜索结果
        searchResults.innerHTML = '';
        // 隐藏下拉菜单
        searchResults.style.display = 'none';
    }
});

document.addEventListener('click', function(event) {
    if (!searchResults.contains(event.target)) {
        // 点击页面其他区域，隐藏下拉菜单
        searchResults.style.display = 'none';
    }
});