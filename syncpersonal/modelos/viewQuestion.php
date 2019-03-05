<?php
    include_once 'db_functions.php';
    $count_question = 0;

    $db = new DB_Functions();
    $questions = $db->getQuestions();
    if ($questions != false)
        $no_of_questions = mysqli_num_rows($questions);
    else
        $no_of_questions = 0;
		
?>
<?php
    if ($no_of_questions > 0) {
?>
 <table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">#</th>
      <th scope="col">Descripcion</th>
      <th scope="col">Punto</th>
      <th scope="col">Dimension</th>
    </tr>
  </thead>
  <tbody id="myTable">
     <?php
             while ($row = mysqli_fetch_array($questions)) {
                $count_question=$count_question+1
        ?>
    <tr>
      <th scope="row"><?php echo $count_question ?></th>
      <td><?php echo $row["description"] ?></td>
      <td><?php echo $db->getPoint($row["point_id"]) ?></td>
      <td><?php echo $db->getAspect($row["aspect_id"]) ?></td>
    </tr>
    <?php } ?>
  </tbody>
</table>
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
  <?php } ?>