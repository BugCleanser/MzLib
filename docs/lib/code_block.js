document.addEventListener('DOMContentLoaded', (event) => {
    hljs.configure({
        cssSelector: 'code'
    });
    hljs.highlightAll();
    document.querySelectorAll('.code-block .copy-btn').forEach(button => {
        button.addEventListener('click', () => {
            const codeContainer = button.closest('.code-block');
            const codeElement = codeContainer.querySelector('code');
            const codeText = codeElement.textContent;
            navigator.clipboard.writeText(codeText).then(() => {
                if(button.classList.contains('copied'))
                    return;
                const originalText = button.innerHTML;
                button.replaceChildren(Object.assign(document.createElement('i'), { className: 'fas fa-check' }), ' 已复制');
                button.classList.add('copied');
                setTimeout(() => {
                    button.innerHTML = originalText;
                    button.classList.remove('copied');
                }, 2000);
            }).catch(err => {
                console.error('复制失败：', err);
            });
        });
    });
});