<?php
    include_once 'db_functions.php';
    $count_summary = 0;

    $db = new DB_Functions();
    $summary = $db->getAllSummary();
    if ($summary != false)
        $no_of_summary = mysqli_num_rows($summary);
    else
        $no_of_summary = 0;
		
?>

<?php
    if ($no_of_summary > 0) {
?>
 <div id="accordion" role="tablist">
 	<?php
 		while($row = mysqli_fetch_array($summary)){
 			$count_summary= $count_summary+1;
 			$facility = mysqli_fetch_array($db->getFacility($row["facility_id"]));
 			$facility_name = $facility["name"];

		?>
			 <div class="card">
    <div class="card-header" role="tab" id="<?php echo "heading".$count_summary ?>">
      <h5 class="mb-0">
        <a data-toggle="collapse" href="<?php echo "#collapse".$count_summary ?>" aria-expanded="true" aria-controls="<?php echo "collapse".$count_summary ?>">
          Resumen Puntos Laborales <?php echo $row["created"]?> - Centro <?php echo $facility_name?>
        </a>
      </h5>
    </div>

    <div id="<?php echo "collapse".$count_summary ?>" class="collapse" role="tabpanel" aria-labelledby="<?php echo "heading".$count_summary ?>">
      <div class="card-body">
        <textarea id="text" onkeyup="do_resize(this);" disabled cols="100"><?php echo $row["content"]; ?></textarea>
      </div>
    </div>
  </div>
 </div>
<?php }?> 
<?php }else{ ?>
<div id="norecord">
No records in MySQL DB
</div>
  <?php } ?>

 <script>

function do_resize(textbox) {

 var maxrows=5; 
  var txt=textbox.value;
  var cols=textbox.cols;

 var arraytxt=txt.split('\n');
  var rows=arraytxt.length; 

 for (i=0;i<arraytxt.length;i++) 
  rows+=parseInt(arraytxt[i].length/cols);

 if (rows>maxrows) textbox.rows=maxrows;
  else textbox.rows=rows;
 }

</script>