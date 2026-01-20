// projects.js - интерактивность для страницы проектов

document.addEventListener('DOMContentLoaded', function() {
    console.log('Project page loaded');
    
    // Управление модальным окном
    window.showCreateModal = function() {
        const modal = document.getElementById('createTaskModal');
        if (modal) {
            modal.style.display = 'flex';
            document.body.style.overflow = 'hidden';
        }
    };
    
    window.hideCreateModal = function() {
        const modal = document.getElementById('createTaskModal');
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    };
    
    // Закрытие модального окна при клике вне его
    document.getElementById('createTaskModal')?.addEventListener('click', function(e) {
        if (e.target === this) {
            hideCreateModal();
        }
    });
    
    // Закрытие по Escape
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            hideCreateModal();
        }
    });
    
    // Переключение статуса чекбоксом
    document.querySelectorAll('.task-status-toggle').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const taskId = this.dataset.taskId;
            const newStatus = this.checked ? 'DONE' : 'TODO';
            updateTaskStatus(taskId, newStatus, this);
        });
    });
    
    // Переключение вида (list/grid)
    document.querySelectorAll('.view-controls .btn').forEach(btn => {
        btn.addEventListener('click', function() {
            document.querySelectorAll('.view-controls .btn').forEach(b => {
                b.classList.remove('active');
                b.classList.add('btn-secondary');
            });
            
            this.classList.add('active');
            this.classList.remove('btn-secondary');
            
            const view = this.querySelector('i').classList.contains('fa-list') ? 'list' : 'grid';
            toggleView(view);
        });
    });
    
    // Подтверждение удаления
    document.querySelectorAll('form button[type="submit"]').forEach(btn => {
        if (btn.closest('form').action.includes('/delete')) {
            btn.addEventListener('click', function(e) {
                if (!confirm('Are you sure you want to delete this task?')) {
                    e.preventDefault();
                }
            });
        }
    });
});

// Функция обновления статуса задачи
async function updateTaskStatus(taskId, status, checkbox) {
    try {
        const projectId = window.location.pathname.split('/')[2];
        const url = `/projects/${projectId}/tasks/${taskId}/status`;
        
        const formData = new FormData();
        formData.append('status', status);
        formData.append('_csrf', document.querySelector('input[name="_csrf"]')?.value);
        
        const response = await fetch(url, {
            method: 'POST',
            body: formData
        });
        
        if (!response.ok) {
            throw new Error('Failed to update status');
        }
        
        // Обновляем UI
        const statusBadge = checkbox.closest('.task-card').querySelector('.status-badge');
        statusBadge.textContent = status;
        statusBadge.className = `status-badge status-${status.toLowerCase()}`;
        
        showNotification(`Task status updated to ${status}`, 'success');
        
    } catch (error) {
        console.error('Error updating task status:', error);
        checkbox.checked = !checkbox.checked; // Откатываем чекбокс
        showNotification('Failed to update task status', 'error');
    }
}

// Функция переключения вида
function toggleView(view) {
    const tasksList = document.querySelector('.tasks-list');
    if (view === 'grid') {
        tasksList.style.display = 'grid';
        tasksList.style.gridTemplateColumns = 'repeat(auto-fill, minmax(300px, 1fr))';
        tasksList.style.gap = '1rem';
    } else {
        tasksList.style.display = 'block';
    }
}

// Быстрые действия
function markAllDone() {
    if (confirm('Mark all visible tasks as DONE?')) {
        document.querySelectorAll('.task-status-toggle').forEach(cb => {
            if (!cb.checked) {
                cb.checked = true;
                cb.dispatchEvent(new Event('change'));
            }
        });
    }
}

function exportTasks() {
    showNotification('Export feature coming soon!', 'info');
}

// Уведомления
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i>
        <span>${message}</span>
        <button class="notification-close"><i class="fas fa-times"></i></button>
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.classList.add('show');
    }, 10);
    
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 300);
    }, 3000);
    
    notification.querySelector('.notification-close').addEventListener('click', () => {
        notification.remove();
    });
}

// CSS для уведомлений (добавить в main.css)
const notificationStyles = document.createElement('style');
notificationStyles.textContent = `
.notification {
    position: fixed;
    top: 1rem;
    right: 1rem;
    background: white;
    border-radius: 0.5rem;
    padding: 1rem 1.5rem;
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    gap: 0.75rem;
    z-index: 1001;
    transform: translateX(120%);
    transition: transform 0.3s ease;
    border-left: 4px solid #4299e1;
    max-width: 400px;
}

.notification.show {
    transform: translateX(0);
}

.notification-success {
    border-left-color: #48bb78;
}

.notification-error {
    border-left-color: #f56565;
}

.notification-info {
    border-left-color: #4299e1;
}

.notification i {
    font-size: 1.25rem;
}

.notification-success i { color: #48bb78; }
.notification-error i { color: #f56565; }
.notification-info i { color: #4299e1; }

.notification span {
    flex: 1;
    font-size: 0.875rem;
}

.notification-close {
    background: none;
    border: none;
    color: #a0aec0;
    cursor: pointer;
    padding: 0.25rem;
    border-radius: 0.25rem;
}

.notification-close:hover {
    background: #f7fafc;
    color: #4a5568;
}
`;
document.head.appendChild(notificationStyles);