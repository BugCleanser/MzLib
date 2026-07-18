{
    const template = document.getElementById("card");

    customElements.define("ui-card", class extends HTMLElement {
        constructor() {
            super();
            this.attachShadow({ mode: 'open' });
            this.shadowRoot.appendChild(template.content.cloneNode(true));
        }
    });
}
