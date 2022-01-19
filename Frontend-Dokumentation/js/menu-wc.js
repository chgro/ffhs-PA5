'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">frontend documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' : 'data-target="#xs-components-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' :
                                            'id="xs-components-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' : 'data-target="#xs-injectables-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' :
                                        'id="xs-injectables-links-module-AppModule-5f1f1c726cc6a1857b3da3ef398ace879b34fdf8a35dbf7a64bb84c558acd91482bb2e2f6fffa3f5f096824c035b03f4a4c9cc4454c30f8ed30d971f5e6a3ee9"' }>
                                        <li class="link">
                                            <a href="injectables/HttpWrapper.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HttpWrapper</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link" >AppRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/HeaderModule.html" data-type="entity-link" >HeaderModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-HeaderModule-3ece7b65fee515c562cf40c663cae7774d09e50bfe7283eb359ccbd2595bf8114c74882457fa85c5446372f9fce7ee221acca570997d99b3ff698de07c6d0916"' : 'data-target="#xs-components-links-module-HeaderModule-3ece7b65fee515c562cf40c663cae7774d09e50bfe7283eb359ccbd2595bf8114c74882457fa85c5446372f9fce7ee221acca570997d99b3ff698de07c6d0916"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-HeaderModule-3ece7b65fee515c562cf40c663cae7774d09e50bfe7283eb359ccbd2595bf8114c74882457fa85c5446372f9fce7ee221acca570997d99b3ff698de07c6d0916"' :
                                            'id="xs-components-links-module-HeaderModule-3ece7b65fee515c562cf40c663cae7774d09e50bfe7283eb359ccbd2595bf8114c74882457fa85c5446372f9fce7ee221acca570997d99b3ff698de07c6d0916"' }>
                                            <li class="link">
                                                <a href="components/HeaderComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HeaderComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/HomeModule.html" data-type="entity-link" >HomeModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-HomeModule-4a9cbce97d4006278a3a23497a63213a2cc232f3d4c96c16fd33f89832af45cc595ff0db029d17d3e246b914645af256062c1faf1f7b6ce4ccc833af2db2c7f1"' : 'data-target="#xs-components-links-module-HomeModule-4a9cbce97d4006278a3a23497a63213a2cc232f3d4c96c16fd33f89832af45cc595ff0db029d17d3e246b914645af256062c1faf1f7b6ce4ccc833af2db2c7f1"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-HomeModule-4a9cbce97d4006278a3a23497a63213a2cc232f3d4c96c16fd33f89832af45cc595ff0db029d17d3e246b914645af256062c1faf1f7b6ce4ccc833af2db2c7f1"' :
                                            'id="xs-components-links-module-HomeModule-4a9cbce97d4006278a3a23497a63213a2cc232f3d4c96c16fd33f89832af45cc595ff0db029d17d3e246b914645af256062c1faf1f7b6ce4ccc833af2db2c7f1"' }>
                                            <li class="link">
                                                <a href="components/HomeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HomeComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/ImpressumModule.html" data-type="entity-link" >ImpressumModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-ImpressumModule-e41a67e7d9212d6857b734adc25dce8af88b860c2071ef9ddcc65d941fe1fe588c76c0068485a1e6f8a7658c2eeb74613eb94d69e25f50e98df667b46be6bf30"' : 'data-target="#xs-components-links-module-ImpressumModule-e41a67e7d9212d6857b734adc25dce8af88b860c2071ef9ddcc65d941fe1fe588c76c0068485a1e6f8a7658c2eeb74613eb94d69e25f50e98df667b46be6bf30"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ImpressumModule-e41a67e7d9212d6857b734adc25dce8af88b860c2071ef9ddcc65d941fe1fe588c76c0068485a1e6f8a7658c2eeb74613eb94d69e25f50e98df667b46be6bf30"' :
                                            'id="xs-components-links-module-ImpressumModule-e41a67e7d9212d6857b734adc25dce8af88b860c2071ef9ddcc65d941fe1fe588c76c0068485a1e6f8a7658c2eeb74613eb94d69e25f50e98df667b46be6bf30"' }>
                                            <li class="link">
                                                <a href="components/ImpressumComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ImpressumComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/SharedModule.html" data-type="entity-link" >SharedModule</a>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-SharedModule-bc5d1a36270d87787914bb4db0c39fd56a0f75800676742603c66493f8cccd0914c669782435f749b652e03545bd5bc7ffa069a3f3c91a4a355e06a587e15d50"' : 'data-target="#xs-injectables-links-module-SharedModule-bc5d1a36270d87787914bb4db0c39fd56a0f75800676742603c66493f8cccd0914c669782435f749b652e03545bd5bc7ffa069a3f3c91a4a355e06a587e15d50"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-SharedModule-bc5d1a36270d87787914bb4db0c39fd56a0f75800676742603c66493f8cccd0914c669782435f749b652e03545bd5bc7ffa069a3f3c91a4a355e06a587e15d50"' :
                                        'id="xs-injectables-links-module-SharedModule-bc5d1a36270d87787914bb4db0c39fd56a0f75800676742603c66493f8cccd0914c669782435f749b652e03545bd5bc7ffa069a3f3c91a4a355e06a587e15d50"' }>
                                        <li class="link">
                                            <a href="injectables/ModuleService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ModuleService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/SpecialisationService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SpecialisationService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/StudyPlanService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >StudyPlanService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/StudyProgramService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >StudyProgramService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/ValidatorService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ValidatorService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/StudyPlanModule.html" data-type="entity-link" >StudyPlanModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-StudyPlanModule-4db07fa1674640b2cb30ea2642c8d4fd27c59868f78bb1f7de6675d3f31e57888d3c1fcf560f8e315d17c5f62ad9597bf8ef65143d34856e9b28ed724df75fe3"' : 'data-target="#xs-components-links-module-StudyPlanModule-4db07fa1674640b2cb30ea2642c8d4fd27c59868f78bb1f7de6675d3f31e57888d3c1fcf560f8e315d17c5f62ad9597bf8ef65143d34856e9b28ed724df75fe3"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-StudyPlanModule-4db07fa1674640b2cb30ea2642c8d4fd27c59868f78bb1f7de6675d3f31e57888d3c1fcf560f8e315d17c5f62ad9597bf8ef65143d34856e9b28ed724df75fe3"' :
                                            'id="xs-components-links-module-StudyPlanModule-4db07fa1674640b2cb30ea2642c8d4fd27c59868f78bb1f7de6675d3f31e57888d3c1fcf560f8e315d17c5f62ad9597bf8ef65143d34856e9b28ed724df75fe3"' }>
                                            <li class="link">
                                                <a href="components/StudyPlanComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >StudyPlanComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/StudyPlanStartComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >StudyPlanStartComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#classes-links"' :
                            'data-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/ModuleTypeConstans.html" data-type="entity-link" >ModuleTypeConstans</a>
                            </li>
                            <li class="link">
                                <a href="classes/NewModulePlan.html" data-type="entity-link" >NewModulePlan</a>
                            </li>
                            <li class="link">
                                <a href="classes/NewStudyPlan.html" data-type="entity-link" >NewStudyPlan</a>
                            </li>
                            <li class="link">
                                <a href="classes/ProgramConstants.html" data-type="entity-link" >ProgramConstants</a>
                            </li>
                            <li class="link">
                                <a href="classes/SemesterTypeConstants.html" data-type="entity-link" >SemesterTypeConstants</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/HttpWrapper.html" data-type="entity-link" >HttpWrapper</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ModuleService.html" data-type="entity-link" >ModuleService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SpecialisationService.html" data-type="entity-link" >SpecialisationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/StudyPlanService.html" data-type="entity-link" >StudyPlanService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/StudyProgramService.html" data-type="entity-link" >StudyProgramService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ValidatorService.html" data-type="entity-link" >ValidatorService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/Module.html" data-type="entity-link" >Module</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ModulePlan.html" data-type="entity-link" >ModulePlan</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Relevance.html" data-type="entity-link" >Relevance</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/SemesterPlan.html" data-type="entity-link" >SemesterPlan</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/StudyPlan.html" data-type="entity-link" >StudyPlan</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/functions.html" data-type="entity-link">Functions</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});