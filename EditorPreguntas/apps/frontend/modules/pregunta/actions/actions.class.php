<?php

/**
 * pregunta actions.
 *
 * @package    EditorPreguntas
 * @subpackage pregunta
 * @author     Your name here
 * @version    SVN: $Id: actions.class.php 23810 2009-11-12 11:07:44Z Kris.Wallsmith $
 */
class preguntaActions extends sfActions {

    /**
     * Executes index action
     *
     * @param sfRequest $request A request object
     */
    public function executeIndex(sfWebRequest $request) {
        $this->pager = new sfDoctrinePager(
                        "EcdlPregunta",
                        sfConfig::get("app_max_questions_result"));

        $this->pager->setQuery(EcdlPreguntaTable::getInstance()->getPreguntas());
        $this->pager->setPage($request->getParameter('page', 1));
        $this->pager->init();

        $this->preguntas = $this->pager->getResults();
    }

    public function executeNew(sfWebRequest $request) {
        $this->form = new EcdlPreguntaForm();
    }

    public function executeCreate(sfWebRequest $request) {
        $this->form = new EcdlPreguntaForm();
        $this->processForm($request, $this->form);
        $this->setTemplate('new');
    }

    public function executeEdit(sfWebRequest $request) {
        $this->forward404Unless($ecdl_pregunta = Doctrine_Core::getTable('EcdlPregunta')->find(array($request->getParameter('id'))), sprintf('Object ecdl_pregunta does not exist (%s).', $request->getParameter('id')));

        $this->pregunta = $this->getRoute()->getObject();

        $this->form = new EcdlPreguntaForm($this->pregunta);
    }

    public function executeUpdate(sfWebRequest $request) {
        $this->forward404Unless($request->isMethod(sfRequest::POST) || $request->isMethod(sfRequest::PUT));
        $this->forward404Unless($ecdl_pregunta = Doctrine_Core::getTable('EcdlPregunta')->find(array($request->getParameter('id'))), sprintf('Object ecdl_pregunta does not exist (%s).', $request->getParameter('id')));
        $this->form = new EcdlPreguntaForm($ecdl_pregunta);

        $this->processForm($request, $this->form);

        $this->setTemplate('edit');
    }
    
    public function executeShow(sfWebRequest $request){
        $this->form = Doctrine_Core::getTable('EcdlPregunta')->find(array($request->getParameter('id')));
    }
    
    public function executeDelete(sfWebRequest $request){
        $this->form = Doctrine_Core::getTable('EcdlPregunta')->find(array($request->getParameter('id')));
        $this->form->delete();
        
        // Redirect
        $this->redirect('pregunta_index');
    }

    protected function processForm(sfWebRequest $request, sfForm $form) {
        // Bind
        $form->bind(
                $request->getParameter($form->getName()), $request->getFiles($form->getName())
        );

        if ($form->isValid()) {
            if ($form->isUniqueAnswerCorrect()) {
                $pregunta = $form->save();

                // Redirect
                $this->redirect('pregunta_index');
            } else {
                // Pass Error flag
                $this->unique_error = true;
            }
        }
    }

}