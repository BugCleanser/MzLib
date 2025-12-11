const cssPath = new URL('card.css', document.currentScript.src).pathname;

let classCard = () => class extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        this.render();
    }

    render() {
        const icon = `
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM13 17H11V15H13V17ZM13 13H11V7H13V13Z" fill="currentColor"/>
                    </svg>
                `;

        this.shadowRoot.innerHTML = `
                    <link rel="stylesheet" href="${cssPath}">
                    <div class="card-attention">
                        <div class="icon-container">
                            ${icon}
                        </div>
                        <div class="content">
                            <slot>${this.innerHTML}</slot>
                        </div>
                    </div>
                `;
    }
};

customElements.define('card-attention', classCard());