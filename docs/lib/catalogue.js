document.addEventListener('DOMContentLoaded', (event) => {
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
});