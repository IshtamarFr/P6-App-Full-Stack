import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleResponse } from '../interfaces/articleResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class MessagesService {
  private pathService = 'api/article';

  constructor(private httpClient: HttpClient) {}

  public send(
    articleId: number,
    messageRequest: string
  ): Observable<ArticleResponse> {
    return this.httpClient.post<ArticleResponse>(
      `${this.pathService}/${articleId}/comments`,
      messageRequest
    );
  }
}
