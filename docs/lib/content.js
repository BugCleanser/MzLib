document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('[element-name]').forEach(element => {
        const newTagName = element.getAttribute('element-name').toLowerCase();

        // 创建新元素并继承所有属性
        const newElement = document.createElement(newTagName);

        // 继承所有属性（除了element-name）
        for (const attr of element.attributes) {
            if (attr.name !== 'element-name') {
                newElement.setAttribute(attr.name, attr.value);
            }
        }

        // 继承所有子节点（包括文本节点）
        while (element.firstChild) {
            newElement.appendChild(element.firstChild);
        }

        // 继承原始元素的所有事件监听器（通过克隆方式）
        element.parentNode.replaceChild(newElement, element);
    });
});