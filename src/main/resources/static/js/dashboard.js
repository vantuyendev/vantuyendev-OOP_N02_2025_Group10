// Modern Dashboard JavaScript for Shop Management System

class DashboardManager {
    constructor() {
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.animateCounters();
        this.setupTableSorting();
        this.setupFormValidation();
        this.setupModalHandlers();
    }

    setupEventListeners() {
        // Navigation active state
        this.setActiveNavLink();
        
        // Search functionality
        this.setupSearch();
        
        // Responsive menu toggle
        this.setupMobileMenu();
    }

    setActiveNavLink() {
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
        
        navLinks.forEach(link => {
            const href = link.getAttribute('href');
            if (currentPath.includes(href) && href !== '/shop/') {
                link.classList.add('active');
            } else if (currentPath === '/shop/' && href === '/shop/') {
                link.classList.add('active');
            }
        });
    }

    animateCounters() {
        const counters = document.querySelectorAll('.stat-number');
        
        counters.forEach(counter => {
            const target = parseInt(counter.textContent);
            const duration = 2000; // 2 seconds
            const increment = target / (duration / 16); // 60fps
            let current = 0;
            
            const updateCounter = () => {
                if (current < target) {
                    current += increment;
                    counter.textContent = Math.ceil(current);
                    requestAnimationFrame(updateCounter);
                } else {
                    counter.textContent = target;
                }
            };
            
            // Start animation when element is visible
            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        updateCounter();
                        observer.unobserve(entry.target);
                    }
                });
            });
            
            observer.observe(counter);
        });
    }

    setupTableSorting() {
        const tables = document.querySelectorAll('.modern-table');
        
        tables.forEach(table => {
            const headers = table.querySelectorAll('th[data-sortable]');
            
            headers.forEach(header => {
                header.style.cursor = 'pointer';
                header.innerHTML += ' <span class="sort-icon">↕️</span>';
                
                header.addEventListener('click', () => {
                    this.sortTable(table, header);
                });
            });
        });
    }

    sortTable(table, header) {
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));
        const columnIndex = Array.from(header.parentNode.children).indexOf(header);
        const isAscending = header.classList.contains('sort-asc');
        
        // Remove existing sort classes
        header.parentNode.querySelectorAll('th').forEach(th => {
            th.classList.remove('sort-asc', 'sort-desc');
            const icon = th.querySelector('.sort-icon');
            if (icon) icon.textContent = '↕️';
        });
        
        // Sort rows
        rows.sort((a, b) => {
            const aValue = a.children[columnIndex].textContent.trim();
            const bValue = b.children[columnIndex].textContent.trim();
            
            // Try to parse as numbers
            const aNum = parseFloat(aValue);
            const bNum = parseFloat(bValue);
            
            let comparison;
            if (!isNaN(aNum) && !isNaN(bNum)) {
                comparison = aNum - bNum;
            } else {
                comparison = aValue.localeCompare(bValue);
            }
            
            return isAscending ? -comparison : comparison;
        });
        
        // Update header classes and icon
        header.classList.add(isAscending ? 'sort-desc' : 'sort-asc');
        const icon = header.querySelector('.sort-icon');
        if (icon) icon.textContent = isAscending ? '↓' : '↑';
        
        // Reorder rows in DOM
        rows.forEach(row => tbody.appendChild(row));
    }

    setupSearch() {
        const searchInputs = document.querySelectorAll('.search-input');
        
        searchInputs.forEach(input => {
            input.addEventListener('input', (e) => {
                this.filterTable(e.target.value, e.target.dataset.target);
            });
        });
    }

    filterTable(searchTerm, tableSelector) {
        const table = document.querySelector(tableSelector);
        if (!table) return;
        
        const rows = table.querySelectorAll('tbody tr');
        const term = searchTerm.toLowerCase();
        
        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(term) ? '' : 'none';
        });
    }

    setupFormValidation() {
        const forms = document.querySelectorAll('.needs-validation');
        
        forms.forEach(form => {
            form.addEventListener('submit', (e) => {
                if (!form.checkValidity()) {
                    e.preventDefault();
                    e.stopPropagation();
                }
                form.classList.add('was-validated');
            });
        });
    }

    setupModalHandlers() {
        // Delete confirmation modals
        const deleteButtons = document.querySelectorAll('[data-action="delete"]');
        
        deleteButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                e.preventDefault();
                const id = button.dataset.id;
                const type = button.dataset.type;
                
                this.showDeleteConfirmation(id, type, button.href);
            });
        });
    }

    showDeleteConfirmation(id, type, deleteUrl) {
        const modal = this.createModal({
            title: `Delete ${type}`,
            message: `Are you sure you want to delete this ${type}? This action cannot be undone.`,
            type: 'danger',
            confirmText: 'Delete',
            cancelText: 'Cancel'
        });
        
        modal.onConfirm = () => {
            window.location.href = deleteUrl;
        };
    }

    createModal({ title, message, type = 'info', confirmText = 'OK', cancelText = 'Cancel' }) {
        const modalHtml = `
            <div class="modal-overlay" id="customModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">${title}</h5>
                            <button type="button" class="close-modal">&times;</button>
                        </div>
                        <div class="modal-body">
                            <p>${message}</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn-modern btn-outline cancel-btn">${cancelText}</button>
                            <button type="button" class="btn-modern btn-${type} confirm-btn">${confirmText}</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        
        document.body.insertAdjacentHTML('beforeend', modalHtml);
        const modal = document.getElementById('customModal');
        
        // Modal event handlers
        const confirmBtn = modal.querySelector('.confirm-btn');
        const cancelBtn = modal.querySelector('.cancel-btn');
        const closeBtn = modal.querySelector('.close-modal');
        
        const closeModal = () => {
            modal.remove();
        };
        
        const modalObj = {
            onConfirm: () => {},
            close: closeModal
        };
        
        confirmBtn.addEventListener('click', () => {
            modalObj.onConfirm();
            closeModal();
        });
        
        cancelBtn.addEventListener('click', closeModal);
        closeBtn.addEventListener('click', closeModal);
        
        // Close on overlay click
        modal.addEventListener('click', (e) => {
            if (e.target === modal) closeModal();
        });
        
        return modalObj;
    }

    setupMobileMenu() {
        const toggleBtn = document.querySelector('.navbar-toggle');
        const navMenu = document.querySelector('.navbar-nav');
        
        if (toggleBtn && navMenu) {
            toggleBtn.addEventListener('click', () => {
                navMenu.classList.toggle('active');
            });
        }
    }

    // Utility methods
    showNotification(message, type = 'success') {
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 1rem 1.5rem;
            background: var(--${type}-color);
            color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--card-shadow);
            z-index: 10000;
            transform: translateX(400px);
            transition: transform 0.3s ease;
        `;
        
        document.body.appendChild(notification);
        
        // Animate in
        setTimeout(() => {
            notification.style.transform = 'translateX(0)';
        }, 100);
        
        // Remove after 5 seconds
        setTimeout(() => {
            notification.style.transform = 'translateX(400px)';
            setTimeout(() => notification.remove(), 300);
        }, 5000);
    }

    formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    }

    formatDate(date) {
        return new Intl.DateTimeFormat('vi-VN', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        }).format(new Date(date));
    }
}

// Auto-refresh data every 5 minutes
class DataRefresh {
    constructor() {
        this.interval = 5 * 60 * 1000; // 5 minutes
        this.start();
    }

    start() {
        setInterval(() => {
            this.refreshDashboardData();
        }, this.interval);
    }

    async refreshDashboardData() {
        try {
            const response = await fetch('/shop/api/dashboard-stats');
            const data = await response.json();
            
            this.updateStatCards(data);
        } catch (error) {
            console.error('Error refreshing dashboard data:', error);
        }
    }

    updateStatCards(data) {
        const employeeCount = document.querySelector('#employee-count');
        const productCount = document.querySelector('#product-count');
        const customerCount = document.querySelector('#customer-count');
        
        if (employeeCount) employeeCount.textContent = data.employeeCount;
        if (productCount) productCount.textContent = data.productCount;
        if (customerCount) customerCount.textContent = data.customerCount;
    }
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new DashboardManager();
    new DataRefresh();
    
    // Add fade-in animation to main content
    document.querySelector('.dashboard-container')?.classList.add('fade-in-up');
});

// Export for use in other modules
window.DashboardManager = DashboardManager;
