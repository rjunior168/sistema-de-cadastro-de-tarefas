const taskForm = document.getElementById('taskForm');
const taskInput = document.getElementById('taskInput');
const taskList = document.getElementById('taskList');
const API_URL = '/api/tarefas';

window.onload = () => {
  fetch(API_URL)
    .then(res => res.json())
    .then(tarefas => tarefas.forEach(t => renderTask(t)));
};

function renderTask(task) {
  const li = document.createElement('li');
  li.textContent = task.descricao;

  const removeBtn = document.createElement('button');
  removeBtn.textContent = 'Remover';
  removeBtn.classList.add('remove-btn');
  removeBtn.onclick = () => {
    fetch(`${API_URL}/${task.id}`, { method: 'DELETE' })
      .then(() => li.remove());
  };

  li.appendChild(removeBtn);
  taskList.appendChild(li);
}

taskForm.addEventListener('submit', function (e) {
  e.preventDefault();
  const descricao = taskInput.value.trim();
  if (descricao !== '') {
    fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ descricao })
    })
      .then(res => res.json())
      .then(task => {
        renderTask(task);
        taskInput.value = '';
      });
  }
});