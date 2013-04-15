<?php slot('title', 'ECDL - Editar preguntas') ?>
<div class="editar">
    <h2>Lista de Preguntas</h2>

    <form action="<?php echo url_for('pregunta_index'); ?>" method="post">
    <div class="div-25">
        <label>Modulo:</label>
        <select id="modulo" name="modulo">
            <option value='0'></option>
            <?php foreach ($modulos as $m) : ?>
                <option value='<?php echo $m->getId() ?>'
                    <?php if ($modulo == $m->getId()) echo "selected='true'" ?>>
                    <?php echo $m->getNombre() ?>
                </option>
            <?php endforeach; ?>
        </select>
    </div>
    <div class="div-25">
        <label>Dificultad:</label>
        <select id="dificultad" name="dificultad">
            <option value='0'></option>
            <?php foreach ($dificultades as $d) : ?>
                <option value='<?php echo $d->getId() ?>' 
                    <?php if ($dificultad == $d->getId()) echo "selected='true'" ?>>
                        <?php echo $d->getNombre() ?>
                </option>
            <?php endforeach; ?>
        </select>
    </div>
    <div class="div-25">
        <label>Texto:</label>
        <input type="text" id="texto" name="texto" value="<?php echo $text ?>"/>
    </div>

    <div  class="div-25">
        <input type="submit" value="Buscar"/>
    </div>
    </form>

    <table>
        <thead>
            <tr>
                <th>Acciones</th>
                <th>Texto</th>
                <th>Modulo</th>
                <th>Dificultad</th>            
            </tr>    
        </thead>
        <tbody>

            <?php foreach ($preguntas as $p) : ?>
                <tr>
                    <td id="acciones">
                        <a href="<?php echo url_for('pregunta_edit', $p) ?>">
                            <img src="/images/edit.png" alt="Editar"/></a> 
                        | 
                        <a href="<?php echo url_for('pregunta_show', $p) ?>">
                            <img src="/images/delete.png" alt="Borrar"/></a>
                    </td>
                    <td><?php echo $p->getTexto() ?></td>
                    <td><?php echo $p->getEcdlModulo()->getNombre() ?></td>
                    <td><?php echo $p->getEcdlDificultad()->getNombre() ?></td>
                </tr>

            <?php endforeach; ?>
        </tbody>
    </table>


    <?php if ($pager->haveToPaginate()): ?>
        <div class="pagination">
            <a href="<?php echo url_for('pregunta_index') ?>?page=1">
                <img src="/images/first.png" alt="First page" title="First page" />
            </a>

            <a href="<?php echo url_for('pregunta_index') ?>?page=<?php echo $pager->getPreviousPage() ?>">
                <img src="/images/previous.png" alt="Previous page" title="Previous page" />
            </a>

            <?php foreach ($pager->getLinks() as $page): ?>
                <?php if ($page == $pager->getPage()): ?>
                    <?php echo $page ?>
                <?php else: ?>
                    <a href="<?php echo url_for('pregunta_index') ?>?page=<?php echo $page ?>"><?php echo $page ?></a>
                <?php endif; ?>
            <?php endforeach; ?>

            <a href="<?php echo url_for('pregunta_index') ?>?page=<?php echo $pager->getNextPage() ?>">
                <img src="/images/next.png" alt="Next page" title="Next page" />
            </a>

            <a href="<?php echo url_for('pregunta_index') ?>?page=<?php echo $pager->getLastPage() ?>">
                <img src="/images/last.png" alt="Last page" title="Last page" />
            </a>
        </div>
    <?php endif; ?>


    <a href="<?php echo url_for('pregunta_new') ?>">Nueva</a>
</div>