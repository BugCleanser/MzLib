{
    const template = document.getElementById('expand-panel');

    customElements.define('expand-panel', class extends HTMLElement {
        header;
        arrow;
        content;

        constructor() {
            super();
            this.attachShadow({ mode: 'open' });
            this.shadowRoot.appendChild(template.content.cloneNode(true));
            this.header = this.shadowRoot.querySelector('[part="header"]');
            this.arrow = this.shadowRoot.querySelector('[part="arrow"]');
            this.content = this.shadowRoot.querySelector('[part="content"]');
            this.header.addEventListener('click', _ => {
                if (this.hasAttribute('open')) {
                    this.removeAttribute('open');
                } else {
                    this.setAttribute('open', '');
                }
            });
        }

        attributeChangedCallback(name, _, newVal) {
            if (name === 'open') {
                if(newVal === null)
                    delete this.content.style.maxHeight;
                else {
                    this.content.style.setProperty('max-height', '100vh', 'important');
                    setTimeout(() => {
                        this.content.style.maxHeight = null;
                    }, 250);
                }
            }
        }

        static get observedAttributes() {
            return ['open'];
        }
    });
}
