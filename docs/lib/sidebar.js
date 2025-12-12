const sidebarPath = new URL('sidebar.html', document.currentScript.src).pathname;

customElements.define('sidebar-component', class extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        this.loadSidebar();
    }

    async loadSidebar() {
        const response = await fetch(sidebarPath);
        this.shadowRoot.innerHTML = await response.text();

        let currentPath = decodeURIComponent(window.location.pathname);
        if(currentPath.endsWith("/"))
            currentPath = currentPath.substr(0, currentPath.length-1);
        this.shadowRoot.querySelectorAll('a').forEach(link => {
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
        this.shadowRoot.querySelectorAll('li > button.open').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                this.parentElement.classList.toggle('open');
            });
        });
    }
});
