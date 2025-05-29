const API_URL = '/tarefas';
const taskForm = document.getElementById('taskForm');
const taskTitle = document.getElementById('taskTitle');
const taskDesc = document.getElementById('taskDesc');
const priorityInput = document.getElementById('priorityInput');
const statusInput = document.getElementById('statusInput');
const taskList = document.getElementById('taskList');

window.onload = () => {
  fetch(API_URL)
    .then(res => res.json())
    .then(tarefas => tarefas.forEach(t => renderTask(t)));
};

function renderTask(task) {
  const li = document.createElement('li');
  li.innerHTML = `
    <strong>${task.titulo}</strong><br>
    ${task.descricao}<br>
    Prioridade: ${task.prioridade} | Status: ${task.status}
  `;

  const editBtn = document.createElement('button');
  editBtn.textContent = 'Editar';
  editBtn.classList.add('edit-btn');
  editBtn.onclick = () => editTask(task, li);

  const removeBtn = document.createElement('button');
  removeBtn.textContent = 'Remover';
  removeBtn.classList.add('remove-btn');
  removeBtn.onclick = () => {
    fetch(`${API_URL}/deletar/${task.id}`)
      .then(() => li.remove());
  };

  li.appendChild(editBtn);
  li.appendChild(removeBtn);
  taskList.appendChild(li);
}

taskForm.addEventListener('submit', function (e) {
  e.preventDefault();

  const titulo = taskTitle.value.trim();
  const descricao = taskDesc.value.trim();
  const prioridade = priorityInput.value;
  const status = statusInput.value;

  if (titulo && descricao) {
    const novaTarefa = { titulo, descricao, prioridade, status };

    fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(novaTarefa)
    })
      .then(res => res.json())
      .then(task => {
        renderTask(task);
        taskForm.reset();
      });
  }
});

function editTask(task, li) {
  const novoTitulo = prompt('Novo título:', task.titulo);
  const novaDescricao = prompt('Nova descrição:', task.descricao);
  const novaPrioridade = prompt('Nova prioridade (ALTA, MEDIA, BAIXA):', task.prioridade);
  const novoStatus = prompt('Novo status (PENDENTE, EM_ANDAMENTO, CONCLUIDA):', task.status);

  if (novoTitulo && novaDescricao && novaPrioridade && novoStatus) {
    const tarefaAtualizada = {
      ...task,
      titulo: novoTitulo,
      descricao: novaDescricao,
      prioridade: novaPrioridade.toUpperCase(),
      status: novoStatus.toUpperCase()
    };

    fetch(API_URL, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(tarefaAtualizada)
    })
      .then(res => res.json())
      .then(updated => {
        li.remove();
        renderTask(updated);
      });
  }
}
