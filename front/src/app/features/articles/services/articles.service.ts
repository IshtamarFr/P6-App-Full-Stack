import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/features/articles/interfaces/article.interface';
import { ArticleResponse } from '../interfaces/articleResponse.interface';
import { MessageDto } from '../interfaces/message-dto';

@Injectable({
  providedIn: 'root',
})
export class ArticlesService {
  private pathService = 'api';

  constructor(private httpClient: HttpClient) {}

  public all(user_id: number): Observable<Article[]> {
    return this.httpClient.get<Article[]>(
      `${this.pathService}/user/${user_id}/articles`
    );
  }

  public detail(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/article/${id}`);
  }

  public comments(id: string): Observable<Array<MessageDto>> {
    return this.httpClient.get<Array<MessageDto>>(
      `${this.pathService}/article/${id}/comments`
    );
  }

  public create(topic: string, article: Article): Observable<ArticleResponse> {
    return this.httpClient.post<ArticleResponse>(
      `${this.pathService}/topic/${topic}/articles`,
      article
    );
  }
}
