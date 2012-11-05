<?php

/**
 * BaseEcdlPregunta
 * 
 * This class has been auto-generated by the Doctrine ORM Framework
 * 
 * @property integer $id
 * @property integer $modulo_id
 * @property integer $imagen_id
 * @property integer $dificultad_id
 * @property text $texto
 * @property EcdlModulo $EcdlModulo
 * @property EcdlImagen $EcdlImagen
 * @property EcdlDificultad $EcdlDificultad
 * @property Doctrine_Collection $Respuestas
 * 
 * @method integer             getId()             Returns the current record's "id" value
 * @method integer             getModuloId()       Returns the current record's "modulo_id" value
 * @method integer             getImagenId()       Returns the current record's "imagen_id" value
 * @method integer             getDificultadId()   Returns the current record's "dificultad_id" value
 * @method text                getTexto()          Returns the current record's "texto" value
 * @method EcdlModulo          getEcdlModulo()     Returns the current record's "EcdlModulo" value
 * @method EcdlImagen          getEcdlImagen()     Returns the current record's "EcdlImagen" value
 * @method EcdlDificultad      getEcdlDificultad() Returns the current record's "EcdlDificultad" value
 * @method Doctrine_Collection getRespuestas()     Returns the current record's "Respuestas" collection
 * @method EcdlPregunta        setId()             Sets the current record's "id" value
 * @method EcdlPregunta        setModuloId()       Sets the current record's "modulo_id" value
 * @method EcdlPregunta        setImagenId()       Sets the current record's "imagen_id" value
 * @method EcdlPregunta        setDificultadId()   Sets the current record's "dificultad_id" value
 * @method EcdlPregunta        setTexto()          Sets the current record's "texto" value
 * @method EcdlPregunta        setEcdlModulo()     Sets the current record's "EcdlModulo" value
 * @method EcdlPregunta        setEcdlImagen()     Sets the current record's "EcdlImagen" value
 * @method EcdlPregunta        setEcdlDificultad() Sets the current record's "EcdlDificultad" value
 * @method EcdlPregunta        setRespuestas()     Sets the current record's "Respuestas" collection
 * 
 * @package    EditorPreguntas
 * @subpackage model
 * @author     Your name here
 * @version    SVN: $Id: Builder.php 7490 2010-03-29 19:53:27Z jwage $
 */
abstract class BaseEcdlPregunta extends sfDoctrineRecord
{
    public function setTableDefinition()
    {
        $this->setTableName('ecdl_pregunta');
        $this->hasColumn('id', 'integer', null, array(
             'type' => 'integer',
             'primary' => true,
             'autoincrement' => true,
             'unsigned' => true,
             ));
        $this->hasColumn('modulo_id', 'integer', null, array(
             'type' => 'integer',
             'notnull' => true,
             'unsigned' => true,
             ));
        $this->hasColumn('imagen_id', 'integer', null, array(
             'type' => 'integer',
             'notnull' => false,
             'unsigned' => true,
             ));
        $this->hasColumn('dificultad_id', 'integer', null, array(
             'type' => 'integer',
             'notnull' => true,
             'unsigned' => true,
             ));
        $this->hasColumn('texto', 'text', null, array(
             'type' => 'text',
             'notnull' => true,
             ));
    }

    public function setUp()
    {
        parent::setUp();
        $this->hasOne('EcdlModulo', array(
             'local' => 'modulo_id',
             'foreign' => 'id',
             'onDelete' => 'CASCADE'));

        $this->hasOne('EcdlImagen', array(
             'local' => 'imagen_id',
             'foreign' => 'id',
             'onDelete' => 'CASCADE'));

        $this->hasOne('EcdlDificultad', array(
             'local' => 'dificultad_id',
             'foreign' => 'id',
             'onDelete' => 'CASCADE'));

        $this->hasMany('EcdlRespuesta as Respuestas', array(
             'local' => 'id',
             'foreign' => 'pregunta_id'));

        $timestampable0 = new Doctrine_Template_Timestampable();
        $this->actAs($timestampable0);
    }
}