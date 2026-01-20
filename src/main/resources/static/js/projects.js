// projects.js - исправленная версия

document.addEventListener('DOMContentLoaded', function() {
    console.log('Project page loaded');
    
    // ========== ПЕРЕКЛЮЧЕНИЕ ВИДА ==========
    const viewToggles = document.querySelectorAll('.view-toggle');
    const tasksList = document.querySelector('.tasks-list');
    
    if (viewToggles.length && tasksList) {
        viewToggles.forEach(btn => {
            btn.addEventListener('click', function() {
                console.log('View toggle clicked:', this.dataset.view);
                
                // Убираем active со всех кнопок
                viewToggles.forEach(b => {
                    b.classList.remove('active');
                    b.classList.remove('btn-primary');
                    b.classList.add('btn-secondary');
                });
                
                // Добавляем active текущей кнопке
                this.classList.add('active');
                this.classList.remove('btn-secondary');
                this.classList.add('btn-primary');
                
                const view = this.dataset.view;
                
                // Сбрасываем все стили
                tasksList.style.display = '';
                tasksList.style.gridTemplateColumns = '';
                tasksList.style.gap = '';
                tasksList.classList.remove('grid-view');
                
                if (view === 'grid') {
                    console.log('Switching to grid view');
                    tasksList.style.display = 'grid';
                    tasksList.style.gridTemplateColumns = 'repeat(auto-fill, minmax(300px, 1fr))';
                    tasksList.style.gap = '16px';
                    tasksList.classList.add('grid-view');
                } else {
                    console.log('Switching to list view');
                    tasksList.style.display = 'flex';
                    tasksList.style.flexDirection = 'column';
                    tasksList.style.gap = '16px';
                }
            });
        });
    }
    
    // ========== КНОПКА NEW TASK ==========
    const newTaskBtn = document.querySelector('.btn-create-task');
    if (newTaskBtn) {
        newTaskBtn.addEventListener('click', function(e) {
            console.log('New Task button clicked');
            
            // Прокручиваем к форме создания задачи
            const createTaskSection = document.querySelector('.create-task-section');
            if (createTaskSection) {
                createTaskSection.scrollIntoView({ 
                    behavior: 'smooth',
                    block: 'start'
                });
                
                // Добавляем подсветку
                createTaskSection.style.animation = 'highlight 2s ease';
                
                // Убираем подсветку через 2 секунды
                setTimeout(() => {
                    createTaskSection.style.animation = '';
                }, 2000);
                
                // Фокус на поле ввода
                setTimeout(() => {
                    const titleInput = document.getElementById('task-title');
                    if (titleInput) {
                        titleInput.focus();
                        titleInput.scrollIntoView({ block: 'center' });
                    }
                }, 500);
            }
        });
    }
    
    // ========== ОСТАЛЬНАЯ ФУНКЦИОНАЛЬНОСТЬ ==========
    
    // Подтверждение удаления
    document.querySelectorAll('form').forEach(form => {
        if (form.action.includes('/delete')) {
            const submitBtn = form.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.addEventListener('click', function(e) {
                    if (!confirm('Are you sure you want to delete this task? This action cannot be undone.')) {
                        e.preventDefault();
                    }
                });
            }
        }
    });
    
    // Простые функции для кнопок в сайдбаре
    window.markAllDone = function() {
        if (confirm('Mark all visible tasks as DONE?')) {
            // Находим все формы изменения статуса
            document.querySelectorAll('.task-quick-actions form').forEach(form => {
                const doneBtn = form.querySelector('button[value="DONE"]');
                if (doneBtn) {
                    doneBtn.click();
                }
            });
        }
    };
    
    window.exportTasks = function() {
        alert('Export feature will be available soon!');
    };
});

// Добавляем анимацию подсветки в CSS
const highlightStyle = document.createElement('style');
highlightStyle.textContent = `
@keyframes highlight {
    0% { box-shadow: 0 0 0 0 rgba(66, 153, 225, 0.5); }
    50% { box-shadow: 0 0 0 10px rgba(66, 153, 225, 0); }
    100% { box-shadow: none; }
}

.create-task-section.highlighted {
    animation: highlight 2s ease;
}

/* Стили для grid view */
.tasks-list.grid-view .task-card {
    height: 100%;
    display: flex;
    flex-direction: column;
}

.tasks-list.grid-view .task-card-header {
    flex-shrink: 0;
}

.tasks-list.grid-view .task-card-body {
    flex: 1;
}

.tasks-list.grid-view .task-quick-actions {
    margin-top: auto;
}
`;
document.head.appendChild(highlightStyle);