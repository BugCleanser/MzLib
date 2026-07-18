document.addEventListener('DOMContentLoaded', _ => {
    function initializeScrollHighlight(container) {
        const associations = Array.from(container.querySelectorAll("li")).reduce((acc, listItem) => {
            const anchorElement = listItem.querySelector("a");
            const targetId = anchorElement.getAttribute("href").substring(1);
            const targetElement = document.getElementById(targetId);
            if (targetElement) {
                acc.push({
                    item: listItem,
                    target: targetElement,
                    anchor: anchorElement
                });
            }
            return acc;
        }, []);
        const updateCurrentHighlight = () => {
            const currentVisible = associations.find(assoc => isElementInViewport(assoc.target));
            if (currentVisible) {
                associations.forEach(assoc => assoc.item.classList.remove("current"));
                currentVisible.item.classList.add("current");
            }
        };
        updateCurrentHighlight();
        window.addEventListener("scroll", updateCurrentHighlight);
        window.addEventListener("resize", updateCurrentHighlight);
    }

    function isElementInViewport(element) {
        const rect = element.getBoundingClientRect();
        return [
            rect.top >= 0,
            rect.left >= 0,
            !(rect.bottom > (window.innerHeight || document.documentElement.clientHeight)),
            !(rect.right > (window.innerWidth || document.documentElement.clientWidth))
        ].every(b => b);
    }

    const overviewList = document.querySelector(".catalogue > ul");
    if (overviewList) {
        initializeScrollHighlight(overviewList);
    }
    

    const toggleBtn = document.querySelector('.catalogue-toggle');
    const closeBtn = document.querySelector('.catalogue-close');
    const catalogue = document.querySelector('.catalogue');
    
    let overlay = document.querySelector('.catalogue-overlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.className = 'catalogue-overlay';
        document.body.appendChild(overlay);
    }
    
    function opencatalogue() {
        catalogue.classList.add('open');
        overlay.classList.add('open');
        document.body.style.overflow = 'hidden';
    }
    
    function closecatalogue() {
        catalogue.classList.remove('open');
        overlay.classList.remove('open');
        document.body.style.overflow = '';
    }
    
    if (toggleBtn) toggleBtn.addEventListener('click', opencatalogue);
    if (closeBtn) closeBtn.addEventListener('click', closecatalogue);
    if (overlay) overlay.addEventListener('click', closecatalogue);
    
    document.addEventListener('keydown', e => {
        if (e.key === 'Escape' && catalogue.classList.contains('open')) {
            closecatalogue();
        }
    });
    
    let resizeTimer;
    window.addEventListener('resize', () => {
        clearTimeout(resizeTimer);
        resizeTimer = setTimeout(() => {
            if (window.innerWidth >= 768 && catalogue.classList.contains('open')) {
                closecatalogue();
                document.body.style.overflow = '';
            }
        }, 100);
    });
});
