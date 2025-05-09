document.getElementById('taskForm').addEventListener('submit', function(e) {
  e.preventDefault();

  const input = document.getElementById('taskInput');
  const taskText = input.value.trim();

  if (taskText !== '') {
    const li = document.createElement('li');

    const span = document.createElement('span');
    span.textContent = taskText;

    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = 'Excluir';
    deleteBtn.style.marginLeft = '10px';

    deleteBtn.addEventListener('click', function(e) {
      e.stopPropagation(); // evita marcar como concluída ao clicar no botão
      li.remove();
    });

    span.addEventListener('click', function() {
      li.classList.toggle('concluida');
    });

    li.appendChild(span);
    li.appendChild(deleteBtn);
    document.getElementById('taskList').appendChild(li);

    input.value = '';
  }
});
