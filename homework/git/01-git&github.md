# 🛸 Git & Github 실전

## Git이란?

Git은 프로젝트를 빠르고 효율적으로 관리할 수 있도록 설계된 무료 오픈 소스 **분산 버전 관리 시스템**입니다.

Git은 매우 빠르며 GUI, 호스팅 서비스 및 명령줄 도구(CLI)로 구성된 방대한 생태계를 갖추고 있습니다.

## Git이 필요한 이유?

> [!IMPORTANT]
> git이 필요한 이유를 스스로 생각하고, 아래에 작성해주세요.

### 📝 답안

```text
여기에 작성하세요.

```

---

## 🔍 Git이 무엇인지 차근차근 알아보기

> [!NOTE]
> 실습 구간입니다. 주로 사용하는 IDE를 켜주세요!

> 해당 실습에서 사용된 IDE는 vscode, shell은 Bash입니다.
 
### Repository

프로젝트의 변경 이력이 저장되는 공간입니다. `git init` 명령어를 통해서 레포지토리를 초기화 할 수 있습니다.

```bash
git init
```

해당 명령어를 실행하면 `.git` 폴더가 생성되고 Git 저장소가 됩니다.

> [!TIP] 
> 다른 폴더를 열고 직접 실행해보세요!

> [!WARNING]
> 파일 탐색기에서 `.git` 폴더가 보이지 않을 수 있습니다. Windows11 기준 보기 > 표시 > 숨긴 항목에 체크해주세요.

### Commit

프로젝트 상태를 저장하는 스냅샷입니다.

```bash
git add .
git commit -m "first commit"
```

커밋이 만들어지면 해당 시점의 상태가 기록됩니다.

> [!IMPORTANT]
> 임의로 파일을 3개 생성한 뒤, 직접 커밋을 3회 해보세요.

```bash
# 여기에 사용한 명령어 적어보기


```

<details>
<summary>💡 예시 펼치기</summary>

```bash
touch a.txt b.txt c.txt

git add a.txt
git status  # a.txt만 초록색이여야 함

# 만약 다른 파일(b.txt, c.txt)이 stage 되어있는 경우
git restore --staged b.txt c.txt

git commit -m "file a"  # 같은 방식으로 b와 c도 해보기!
```
</details>

`git log` 명령어를 사용하여 커밋 이력을 볼 수 있습니다.

```bash
git log
git log --oneline  # 한줄로 보기
```

커밋한 내용을 리셋하려면 `git reset`을 사용합니다

```bash
git reset HEAD
```

### Branch

작업 공간을 분리하는 기능입니다.

```text
main
  └── feat/login
  └── fix/admin-page
  └── chore/api-spec
```

`git branch {branch-name}` 명령어로 선택된 브랜치의 이력을 복사하여 코드를 분기하고 관리할 수 있습니다.

```bash
git branch               # 로컬 브랜치 보기
git branch -M main       # 현재 브랜치 명을 main으로 변경
git branch new-branch    # new-branch라는 이름의 브랜치 생성
git checkout new-branch  # 새로 생성한 브랜치로 이동
git checkout -b other    # other 브랜치 생성과 동시에 이동
```

> [!IMPORTANT]
> main 브랜치에서 분기하여 자신만의 브랜치를 만들고, 아무 파일이나 생성 후 커밋해주세요!

```bash
# 여기에 사용한 명령어 적어보기


```

### Merge

브랜치에서 작업한 내용을 합치는 과정입니다.

```text
main
  └── feat/login => 작업 완료
```

`feat/login` 브랜치의 작업이 완료되었으니 main에 작업한 내용을 반영해줍시다.

```bash
git checkout main     # main 브랜치로 이동
git merge feat/login
```

> [!IMPORTANT]
> Branch 단계에서 만들었던 자신의 브랜치를 main에 합쳐주세요

```bash
# 여기에 사용한 명령어 적어보기


```

### Remote

원격 저장소란, 인터넷에 있는 저장소로 git의 변경 이력을 백업할 수 있는 장소입니다.

주요 서비스로는 **Github**, Gitlab, Bitbucket이 있습니다.

내 PC의 저장소는 로컬 저장소, GitHub의 저장소는 원격 저장소라고 칭합니다.

```bash
git remote -v
```

해당 명령어를 통해서 현재 내 로컬 레포와 연결된 원격 레포가 어디인지 확인할 수 있습니다.

원격 저장소로 커밋하기 위해 github repository를 하나 생성해주세요.

[repository](#repository-1)

원격 저장소로 업로드할 때는 `git push`를 사용합니다.

```bash
git push origin main
```

만약 브랜치가 원격과 연결되어있지 않은 경우, 다음 명령어를 사용하세요.

```bash
git push --set-upstream origin main
```

원격 저장소의 내용을 다운로드할 땐 `git pull`을 사용합니다.

```bash
git pull
```

현재 작업한 커밋 이력을 다른 브랜치(main)에서 시작한 것처럼 하고 싶을 때, 즉 브랜치의 시작점을 변경할 때에는 rebase를 사용합니다.

```text
example

A ── B ── C (develop)
      \
       D ── E (feat/login)
```
위와 같은 상황에서 rebase를 사용할 경우
```text
A ── B ── C ── D' ── E'
```
처럼 시작점이 변경됩니다.

```bash
git checkout feat/login  # 실습 시 실제 존재하는 브랜치 사용
git rebase main
git pull --rebase origin main  # 이와 같은 방식도 있습니다.
```

<details>
<summary>Merge와의 차이점</summary>

```bash
git checkout feat/login
git merge develop
```
```text
A ── B ── C ───── M
      \         /
       D ── E ──
```
M이라는 merge 커밋이 발생함.

merge는 브랜치가 많아질수록 복잡해지므로, rebase가 로그 관리하기 더 편리하다는 것을 알 수 있습니다.
</details>

원격 내용 동기화 시 `git fetch`를 사용합니다.
```bash
git fetch origin
```

> [!IMPORTANT]
> push, pull을 직접 해본 뒤, 자신이 사용한 명령어를 작성해주세요

```bash
# 여기에 작성해주세요


```

---

## GitHub 직접 사용해보기

Github는 원격 저장소로, 로컬에서 git을 통해 관리한 내역을 인터넷에 저장할 수 있습니다.

주요 기능으로는 원격 Repository 생성, Issue, Pull Request, 코드 리뷰, Branch Ruleset, GitHub Actions 등이 있습니다.

### Repository

[github](https://github.com)에서 자신만의 새로운 Repository를 하나 생성해주세요. 

### Issue

생성한 레포지토리의 Issues 탭에서 Issue를 생성할 수 있습니다. `New issue` 버튼을 클릭하여 이슈를 생성해주세요. 내용은 상관 없습니다.

<details>
<summary>예시 issue template</summary>

```
---
name: Chore
about: 문서, 설정, CI/CD 등 유지보수 작업
title: "[CHORE] "
labels: chore
---

## 📌 작업 내용

<!-- 어떤 작업인지 작성해주세요 -->

## 🎯 목적

<!-- 왜 필요한 작업인지 작성해주세요 -->

## ✅ TODO

* [ ]

## 📎 참고 자료

<!-- 관련 문서, 링크, 스크린샷 등이 있다면 첨부해주세요 -->
```
</details>

### Pull Request

작업이 끝난 뒤 다른 브랜치로 merge하기 전 코드를 합쳐도 될 지 팀원에게 알리는 과정입니다. 협업 시 일반적으로 merge 명령어보다 pr을 사용하여 머지합니다.

> [!IMPORTANT]
> main 브랜치에서 새로운 브랜치를 하나 생성한 뒤, push 후 pr을 직접 올려보세요.

<details>
<summary>예시 PR template</summary>

```
## 📌 작업 내용

<!-- 이번 PR에서 작업한 내용을 작성해주세요 -->

## 🔍 주요 변경 사항

*

## ✅ 체크리스트

* [ ] 코드가 정상적으로 동작하는지 확인했습니다.
* [ ] 테스트를 수행했습니다.
* [ ] commitlint 검사를 통과했습니다.
* [ ] 관련 문서를 수정했습니다. (필요 시)

## 📎 관련 이슈

Closes #
(관련 이슈 번호를 작성하면 자동으로 링크됩니다.)
(예시: #15)

## 💬 기타 사항

<!-- 리뷰어가 알아야 할 내용이 있다면 작성해주세요 -->
```
</details>

<details>
<summary>Issue & PR 템플릿 지정하는 방법에 대해 알아보기</summary>

### Issue & PR 템플릿
깃허브에는 이슈와 PR 템플릿을 자동으로 적용할 수 있는 기능을 지원합니다.

레포지토리에서 setting > general > features > issues Issue 템플릿을 생성할 수 있습니다.

생성되는 파일의 위치는 `.github/ISSUE_TEMPLATE`입니다.

ISSUE_TEMPLATE 폴더에 템플릿을 생성하면, 이후 이슈를 작성할 때 템플릿을 선택하여 이슈를 작성할 수 있습니다.

PR 템플릿의 경우 직접 생성해야 합니다.

`.github/PULL_REQUEST_TEMPLATE.md` 파일을 생성하면 PR 생성 시 자동으로 템플릿이 적용됩니다.

PR의 경우 템플릿을 적용시키는 창이 따로 지원되지 않습니다. 하지만 여러개의 템플릿을 사용하기 위한 방법 또한 존재합니다.

`.github/PULL_REQUEST_TEMPLATE` 폴더에 파일을 만들고(feature.md, chore.md 등), pr 작성 시 url 끝에 `&template=chore.md` 형식을 붙여주면 템플릿이 적용됩니다.
</details>

### Branch Ruleset

main, develop 같은 브랜치는 통상적으로 개발이 완료된 코드를 합치기 위한 주요 브랜치입니다.

해당 브랜치에는 일반적으로 바로 push를 날리지 않으며, PR 등의 방식으로 다른 브랜치에서 코드를 머지합니다.

그러나 사용자가 실수로 push 하거나, 커밋 히스토리가 꼬이는 것을 방지하기 위해서 깃허브에서는 브랜치 규칙을 지정할 수 있습니다.

```
Settings > Branches > Add branch ruleset
```

주로 사용하는 옵션은 다음과 같습니다.

* Require a pull request before merging : 머지 전 PR 필수
* Block force pushes : 강제 푸시 제한
* Restrict deletions : 브랜치 삭제 제한
* Require status checks to pass : CI 통과 필수

### Github Actions
Actions는 GitHub에서 제공하는 CI/CD(지속적 통합/지속적 배포) 자동화 기능입니다.

보통 `.yml`(`.yaml`) 파일을 이용하여 설정하며, 설정 파일은 `.github/workflows`에 저장됩니다.

해당 섹션은 다음에 더 자세히 다루겠습니다.

## 🌿마무리

이번 실습은 여기까지입니다.

Git은 자주 이용해보며 익히는 것이 가장 빠르니, 코드를 작성할 때 자주 활용해보세요.

다음에는 Github Actions에 대해서 더 다루어보겠습니다.