import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleRoutingModule } from './articles-routing.module';
import { ListComponent } from './components/list/list.component';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';
import { MatIconModule } from '@angular/material/icon';
import { FlexLayoutModule } from '@angular/flex-layout';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TopicComponent } from './components/topic/topic.component';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
registerLocaleData(localeFr);

const materialModules = [
  MatButtonModule,
  MatIconModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatSnackBarModule,
  MatSelectModule,
];

@NgModule({
    imports: [
        CommonModule,
        FlexLayoutModule,
        FormsModule,
        MaterialFileInputModule,
        ReactiveFormsModule,
        ArticleRoutingModule,
        ...materialModules,
        ListComponent, FormComponent, DetailComponent, TopicComponent,
    ],
    providers: [
        {
            provide: LOCALE_ID,
            useValue: 'fr-FR',
        },
    ],
})
export class ArticlesModule {}
