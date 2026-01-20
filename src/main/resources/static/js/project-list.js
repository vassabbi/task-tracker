// projects-list.js - интерактивность для страницы проектов

document.addEventListener('DOMContentLoaded', function() {
    console.log('Projects list page loaded');
    
    // Управление модальным окном
    window.showCreateProjectModal = function() {
        const modal = document.getElementById('createProjectModal');
        if (modal) {
            modal.style.display = 'flex';
            document.body.style.overflow = 'hidden';
            
            // Фокус на поле ввода
            setTimeout(() => {
                const nameInput = modal.querySelector('input[type="text"]');
                if (nameInput) nameInput.focus();
            }, 100);
        }
    };
    
    window.hideCreateProjectModal = function() {
        const modal = document.getElementById('createProjectModal');
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    };
    
    // Закрытие модального окна при клике вне его
    document.getElementById('createProjectModal')?.addEventListener('click', function(e) {
        if (e.target === this) {
            hideCreateProjectModal();
        }
    });
    
    // Закрытие по Escape
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            hideCreateProjectModal();
        }
    });
    
    // Переключение вида (grid/list)
    document.querySelectorAll('.view-toggle').forEach(btn => {
        btn.addEventListener('click', function() {
            // Убираем active со всех кнопок
            document.querySelectorAll('.view-toggle').forEach(b => {
                b.classList.remove('active');
            });
            
            // Добавляем active текущей кнопке
            this.classList.add('active');
            
            const view = this.dataset.view;
            const projectsContainer = document.getElementById('projectsContainer');
            
            if (view === 'list') {
                projectsContainer.classList.add('list-view');
            } else {
                projectsContainer.classList.remove('list-view');
            }
        });
    });
    
    // Подтверждение удаления проекта
    document.querySelectorAll('.delete-form').forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('Are you sure you want to delete this project? All tasks in this project will also be deleted.')) {
                e.preventDefault();
            }
        });
    });
    
    // Выбор цвета в модальном окне
    document.querySelectorAll('.color-option input[type="radio"]').forEach(radio => {
        radio.addEventListener('change', function() {
            // Обновляем визуальное выделение
            document.querySelectorAll('.color-option').forEach(option => {
                option.style.boxShadow = '';
            });
            
            if (this.checked) {
                this.closest('.color-option').style.boxShadow = '0 0 0 3px white, 0 0 0 6px #4f46e5';
            }
        });
    });
    
    // Валидация формы при отправке
    const projectForm = document.getElementById('projectForm');
    if (projectForm) {
        projectForm.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creating...';
            submitBtn.disabled = true;
        });
    }
    
    // Автоматическое закрытие дропдаунов при клике вне
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.dropdown')) {
            document.querySelectorAll('.dropdown-menu').forEach(menu => {
                menu.style.display = 'none';
            });
        }
    });
    
    // Открытие дропдаунов
    document.querySelectorAll('.project-menu-btn').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.stopPropagation();
            const menu = this.closest('.dropdown').querySelector('.dropdown-menu');
            menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
        });
    });
});

// Функция показа уведомлений (можно вынести в общий файл)
function showNotification(message, type = 'info') {
    // Создаем элемент уведомления
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i>
        <span>${message}</span>
        <button class="notification-close"><i class="fas fa-times"></i></button>
    `;
    
    document.body.appendChild(notification);
    
    // Анимация появления
    setTimeout(() => {
        notification.classList.add('show');
    }, 10);
    
    // Автоудаление через 3 секунды
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 300);
    }, 3000);
    
    // Закрытие по клику
    notification.querySelector('.notification-close').addEventListener('click', () => {
        notification.remove();
    });
}

// Добавляем стили для уведомлений, если их еще нет
if (!document.querySelector('#notification-styles')) {
    const style = document.createElement('style');
    style.id = 'notification-styles';
    style.textContent = `
        .notification {
            position: fixed;
            top: 20px;
            right: 20px;
            background: white;
            border-radius: 8px;
            padding: 16px 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            display: flex;
            align-items: center;
            gap: 12px;
            z-index: 1000;
            transform: translateX(120%);
            transition: transform 0.3s ease;
            border-left: 4px solid;
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
            font-size: 20px;
        }
        
        .notification-success i { color: #48bb78; }
        .notification-error i { color: #f56565; }
        .notification-info i { color: #4299e1; }
        
        .notification span {
            flex: 1;
            font-size: 14px;
        }
        
        .notification-close {
            background: none;
            border: none;
            color: #a0aec0;
            cursor: pointer;
            padding: 4px;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .notification-close:hover {
            background: #f7fafc;
            color: #4a5568;
        }
    `;
    document.head.appendChild(style);
}