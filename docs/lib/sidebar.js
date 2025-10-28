document.addEventListener('DOMContentLoaded', function() {
    let currentPath = decodeURIComponent(window.location.pathname);
    if(currentPath.endsWith("/"))
        currentPath = currentPath.substr(0, currentPath.length-1);
    document.querySelectorAll('aside a').forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPath || href === currentPath+"/index") {
            link.closest('li').classList.add('current');
            let parent = link.closest('*:has(ul)');
            while (parent) {
                parent.classList.add('open');
                parent = parent.parentElement?.closest('*:has(ul)');
            }
        }
    });
    document.querySelectorAll('aside li > button.open').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            this.parentElement.classList.toggle('open');
        });
    });
});