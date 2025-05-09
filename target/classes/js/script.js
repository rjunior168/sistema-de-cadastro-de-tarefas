document.getElementById('taskForm').addEventListener('submit', function(e) {
  e.preventDefault();

  const input = document.getElementById('taskInput');
  const taskText = input.value.trim();

  if (taskText !== '') {
    const li = document.createElement('li');
    li.textContent = taskText;

    // Adiciona funcionalidade de marcar como concluída ao clicar
    li.addEventListener('click', function() {
      li.classList.toggle('concluida');
    });

    document.getElementById('taskList').appendChild(li);
    input.value = '';
  }
});
