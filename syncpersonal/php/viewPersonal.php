<?php
    include_once 'db_functions.php';
    $count_personal = 0;

    $db = new DB_Functions();
    $users = $db->getAllPersonal();
    if ($users != false)
        $no_of_users = mysqli_num_rows($users);
    else
        $no_of_users = 0;
		
?>
<?php
    if ($no_of_users > 0) {
?>
 <table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">#</th>
      <th scope="col">Rut</th>
      <th scope="col">Nombre</th>
      <th scope="col">Apellido</th>
    </tr>
  </thead>
  <tbody id="myTable">
     <?php
             while ($row = mysqli_fetch_array($users)) {
                $count_personal=$count_personal+1
        ?>
    <tr>
      <th scope="row"><?php echo $count_personal ?></th>
      <td><?php echo $row["rut"] ?></td>
      <td><?php echo $row["name"] ?></td>
      <td><?php echo $row["surname"] ?></td>
    </tr>
    <?php } ?>
  </tbody>
</table>
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
  <?php } ?>