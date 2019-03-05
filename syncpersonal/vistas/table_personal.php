<?php
    include_once '../modelos/db_functions.php';
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
 <table class="table table-borderless table-hover table-bordered" id="tabla_centro">
    <form id="personal" name="personal" method="post">
      <thead class="thead-default bg-primary text-white">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Rut</th>
          <th scope="col">Nombre</th>
          <th scope="col">Apellido</th>
           <th scope="col">Estado</th>
           <th scope="col">Centro</th>
        </tr>
      </thead>
      <tbody>
         <?php
                 while ($row = mysqli_fetch_array($users)) {
                    $count_personal=$count_personal+1
            ?>
        <tr>
          <th scope="row"><?php echo $count_personal ?></th>
          <td><?php echo $row["rut"]; ?></td>
          <td><?php echo $row["name"]; ?></td>
          <td><?php echo $row["surname"];?></td>
           <td><?php if ($row["state"] == 1) {echo "Habilitado";} else { echo "Deshabilitado";} ?></td>
            <td><?php echo $db->getFacilityForId($row["facility_id"]); ?></td>
        </tr>
        <?php } ?>
      </tbody>
  </form>
</table>
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
  <?php } ?>