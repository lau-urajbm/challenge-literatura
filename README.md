<h1 id="Título-e-imagen-de-portada"> Catálogo de libros </h1>
<img src="https://img.shields.io/badge/Terminado%20-%20status?label=Status">
<h2 id="Descripción">Descripción</h2>
<p>Catálogo de libros realizado en Java con springboot. Se utiliza Postgresql como base de datos. Consume datos de la API Gutendex. </p>

<h2 id="Características">Características</h2>
<p>El programa posee el siguiente menú interactivo el cual permite ver las opciones disponibles: </p>
<ol>
  <li>1- Buscar y registrar Libro</li>
  <li>2- Listar libros registrados</li>
  <li>3- Listar autores registrados</li>
  <li>4- Listar autores vivos en un determinado año</li>
  <li>5- Listar libros por idioma</li>
  <li>6- Salir</li>
</ol>
<h2>Funcionamiento</h2>
<p>Usar el catálogo de libros es muy sencillo. Su funcionamiento basicamente es como primera instancia traer el libro solicitado al marcar 
la primera opción del menú escribiendo su titulo. Se hace una requisición a la api de gutendex, si encuentra algún libro que coincida con lo solicitado
el programa agregará el libro a una base de datos en donde quedará registrado a qué autor corresponde, en qué idioma está disponible y el número de descargas
del libro.</p>
<p>Teniendo la información mencionada anteriormente se podrá hacer uso de las siguientes opciones teniendo en cuenta que debe haber al menos un libro 
registrado en la base de datos para que se muestre algún tipo de información.</p>
<h3>Listar libros registrados</h3>
<p>Con la segunda opción se podran ver todos los libros que han sido guardados en la base de datos con su información correspondiente.</p>
<h3>Listar autores registrados</h3>
<p>Con la tercera opción podremos ver todos los autores que se han agregado al haber agregado nuevos libros a la base de datos con sus datos correspondientes
los cuales serían nombre, fecha de nacimiento y fecha de fallecimiento.</p>
<h3>Listar autores vivos en un determinado año</h3>
<p>Con la cuarta opción podremos introducir un año y el programa buscará los autores de nuestra base de datos que estaban vivos en ese momento con sus respectivos datos.</p>
<h3>Listar libros por idioma</h3>
<p>Ya que no todos los libros están en el mismo idioma, con la quinta opción podremos elegir el idioma en el que deseamos ver los libros que están disponibles 
en nuestra base de datos. Por el momento las opciones disponibles para buscar por idioma son ingles, frances y español.</p>
<h3>Salir</h3>
<p>Finalmente, con la última opción podremos finalizar el programa.</p>
<h2>Tecnologías usadas</h2>
<ul>
  <li>Java</li>
  <li>Spring boot</li>
  <li>Postgresql</li>
</ul>
<h2>Autor</h2>
<p>Laura Bernal</p>
