async function loadProjects() {
    const response = await fetch('/api/projects');
    const projects = await response.json();

    const ul = document.getElementById('projects');
    ul.innerHTML = '';
    projects.forEach(p => {
        const li = document.createElement('li');
        li.textContent = p.name;
        ul.appendChild(li);
    });
}