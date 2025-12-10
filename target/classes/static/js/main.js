// main.js - theme toggle, simple toast, mobile nav, sample chart

document.addEventListener('DOMContentLoaded', () => {
    // theme toggle
    const themeToggle = document.getElementById('themeToggle');
    const body = document.body;
    const saved = localStorage.getItem('theme-mode');
    if (saved === 'dark') body.classList.add('dark');

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            body.classList.toggle('dark');
            localStorage.setItem('theme-mode', body.classList.contains('dark') ? 'dark' : 'light');
            themeToggle.innerText = body.classList.contains('dark') ? '☀️' : '🌙';
        });
        themeToggle.innerText = body.classList.contains('dark') ? '☀️' : '🌙';
    }

    // mobile nav toggle
    const navBtn = document.getElementById('navToggle');
    const navLinks = document.querySelector('.nav-links');
    if (navBtn && navLinks) {
        navBtn.addEventListener('click', () => {
            navLinks.classList.toggle('open');
            navBtn.classList.toggle('open');
        });
    }

    // toast helper
    window.showToast = (msg) => {
        const t = document.createElement('div');
        t.className = 'toast';
        t.innerText = msg;
        document.body.appendChild(t);
        setTimeout(() => t.classList.add('visible'), 20);
        setTimeout(() => t.classList.remove('visible'), 2400);
        setTimeout(() => t.remove(), 2800);
    };

    // simple profile chart (if canvas exists)
    if (document.getElementById('profileChart')) {
        try {
            const ctx = document.getElementById('profileChart').getContext('2d');
            // Chart.js must be included in the page; this code expects it
            new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ['Social', 'Work', 'Finance', 'Other'],
                    datasets: [{
                        data: [8, 4, 3, 2], backgroundColor: [
                            'rgba(11,99,211,0.9)', 'rgba(255,159,67,0.92)', 'rgba(25,169,116,0.9)', 'rgba(155,155,155,0.12)'
                        ]
                    }]
                },
                options: { plugins: { legend: { position: 'bottom' } }, cutout: '60%' }
            });
        } catch (e) {
            // Chart missing - ignore
            console.warn(e);
        }
    }
});
