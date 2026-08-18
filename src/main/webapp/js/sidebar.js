document.addEventListener('DOMContentLoaded', function () {
    const toggleBtn = document.getElementById('sidebarToggle');
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('sidebarOverlay');

    if (!toggleBtn || !sidebar || !overlay) {
        return; // page sans sidebar (ex: login), on ne fait rien
    }

    function openSidebar() {
        sidebar.classList.add('open');
        overlay.classList.add('visible');
    }

    function closeSidebar() {
        sidebar.classList.remove('open');
        overlay.classList.remove('visible');
    }

    toggleBtn.addEventListener('click', function () {
        if (sidebar.classList.contains('open')) {
            closeSidebar();
        } else {
            openSidebar();
        }
    });

    overlay.addEventListener('click', closeSidebar);

    // Ferme automatiquement si on clique un lien du menu (mobile)
    sidebar.querySelectorAll('.nav-item').forEach(function (link) {
        link.addEventListener('click', closeSidebar);
    });

    // Si on repasse en desktop (rotation, redimensionnement), on nettoie l'état mobile
    window.addEventListener('resize', function () {
        if (window.innerWidth > 768) {
            closeSidebar();
        }
    });
});