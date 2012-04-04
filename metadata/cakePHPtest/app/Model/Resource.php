<?php
class Resource extends AppModel {
	public $name = 'Resource';
	
	public $hasOne = array(
    	'PageData' => array(
        	'className'    => 'PageData',
        	'foreignKey'   => 'id',
        	'dependent'    => false
		)
    );
}
?>
