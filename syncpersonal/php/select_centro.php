<?php
    include_once 'db_functions.php';
    $count_personal = 0;

    $db = new DB_Functions();
    $centros = $db->getAllFacility();
    if ($centros != false)
        $no_of_facilities = mysqli_num_rows($centros);
    else
        $no_of_facilities = 0;
		
?>
<?php
    if ($no_of_facilities > 0) {
?>
<select class="custom-select mt-2" id="select_centro">
		
        <option selected>Elija un centro</option>
        <?php  while ($row = mysqli_fetch_array($centros)) { ?>
        <option value="<?php echo $row["name"]?>"><?php echo $row["name"]?></option>
        <?php } ?>
  </select>
   <?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
  <?php } ?>



